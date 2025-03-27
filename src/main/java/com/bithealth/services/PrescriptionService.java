package com.bithealth.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.bithealth.dto.PrescriptionCreateRequestDTO;
import com.bithealth.dto.PrescriptionVerificationDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Prescription;
import com.bithealth.repositories.AppointmentRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.bithealth.repositories.PrescriptionRepository;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Prescription createPrescription(PrescriptionCreateRequestDTO dto) {
        // Fetch the associated Appointment
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Appointment not found with ID: " + dto.getAppointmentId()));

        // Create the Prescription
        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setMedicineList(dto.getMedicineList());
        prescription.setInvoiceNo(dto.getInvoiceNo());
        prescription.setInvoiceDate(dto.getInvoiceDate());
        //TODO: Do a check here before they are allow to set value as True
        prescription.setIsVerified(true);

        return prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionByAppointment(Long appointmentId) {
        return prescriptionRepository.findByAppointment_AppointmentId(appointmentId);
    }

    public Prescription verifyPrescription(Long prescriptionId, PrescriptionVerificationDTO dto) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found with ID: " + prescriptionId));

        if (dto.getIsVerified() == null) {
            throw new IllegalArgumentException("isVerified parameter is required");
        }

        prescription.setIsVerified(dto.getIsVerified());
        return prescriptionRepository.save(prescription);
    }

    public Resource generatePrescriptionFile(Long prescriptionId, String format) throws IOException, DocumentException {
        // Fetch prescription details
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found"));

        // Generate content
        String content = "Prescription Details:\n" +
                "Appointment: " + prescription.getAppointment();

        // Generate file based on format
        if ("pdf".equalsIgnoreCase(format)) {
            return generatePdf(content);
        } else if ("docx".equalsIgnoreCase(format)) {
            return generateWord(content);
        } else {
            throw new IllegalArgumentException("Unsupported format");
        }
    }

    private Resource generatePdf(String content) throws IOException, DocumentException {
        // Use iText or Apache PDFBox to generate PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(new Paragraph(content));
        document.close();

        return new ByteArrayResource(outputStream.toByteArray());
    }

    private Resource generateWord(String content) throws IOException {
        // Use Apache POI to generate Word document
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(content);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();

        return new ByteArrayResource(outputStream.toByteArray());
    }
}