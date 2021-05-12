package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.MedicalReport;

public class MedicalReportDTO {
    private Long id;
    private String description;
    private Long ePrescriptionId;

    public MedicalReportDTO(Long id, String description, Long ePrescriptionId) {
        this.id = id;
        this.description = description;
        this.ePrescriptionId = ePrescriptionId;
    }

    public MedicalReportDTO() {
    }

    public MedicalReportDTO(MedicalReport report) {
        this.id = report.getId();
        this.description = report.getDescription();
        this.ePrescriptionId = report.getEprescription().getId();
    }
}
