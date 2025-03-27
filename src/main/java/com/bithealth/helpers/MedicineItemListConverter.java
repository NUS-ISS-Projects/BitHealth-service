package com.bithealth.helpers;

import com.bithealth.dto.MedicineItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

@Converter(autoApply = false)
public class MedicineItemListConverter implements AttributeConverter<List<MedicineItem>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<MedicineItem> attribute) {
        if(attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting list of medicine items to JSON", e);
        }
    }

    @Override
    public List<MedicineItem> convertToEntityAttribute(String dbData) {
        if(dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<MedicineItem>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to list of medicine items", e);
        }
    }
}
