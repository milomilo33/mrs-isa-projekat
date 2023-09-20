package com.mrsisa.mrsisaprojekat.dto;

public class AddAllergyDTO {
    private String patientEmail;
    private Long medicamentId;

    public AddAllergyDTO() {
    }

    public AddAllergyDTO(String patientEmail, Long medicamentId) {
        this.patientEmail = patientEmail;
        this.medicamentId = medicamentId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String pateintEmail) {
        this.patientEmail = pateintEmail;
    }

    public Long getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(Long medicamentId) {
        this.medicamentId = medicamentId;
    }
}
