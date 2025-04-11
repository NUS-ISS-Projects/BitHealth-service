package com.bithealth.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bithealth.entities.User;
import com.bithealth.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private final JwtValidator jwtValidator;
    private final UserRepository userRepository;

    public FirebaseAuthenticationFilter(JwtValidator jwtValidator, UserRepository userRepository) {
        this.jwtValidator = jwtValidator;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String idToken = authorizationHeader.substring(7); // Remove "Bearer " prefix

            try {
                // Validate the token
                FirebaseToken decodedToken = jwtValidator.validateToken(idToken);

                // Extract the UID from the token
                String firebaseUid = decodedToken.getUid();

                // Look up the local user using the firebaseUid
                User localUser = userRepository.findByFirebaseUid(firebaseUid)
                        .orElseThrow(() -> new RuntimeException("User not found with UID: " + firebaseUid));

                // Create authorities based on the user's role
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                if (localUser.getRole() == User.Role.DOCTOR) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
                } else if (localUser.getRole() == User.Role.PATIENT) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_PATIENT"));
                }

                // Set authentication in the SecurityContext
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        localUser,
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Invalid token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}