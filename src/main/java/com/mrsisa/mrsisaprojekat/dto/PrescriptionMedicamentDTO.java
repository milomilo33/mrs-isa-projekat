package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;

import java.time.LocalDate;
import java.util.HashSet;

public class PrescriptionMedicamentDTO {
    private Long pharmacyId;
    private String patientEmail;
    private Medicament medicament;
    private LocalDate expiryDate;
    private int quantity;
    private Long id;


    public PrescriptionMedicamentDTO() {

    }



    public PrescriptionMedicamentDTO(PrescriptionMedicament pm) {
        this.id = pm.getId();
        this.medicament = pm.getMedicament();
        this.medicament.setSubstituteMedicaments(new HashSet<>());
        this.medicament.setRatings(new HashSet<>());
        this.expiryDate = pm.getExpiryDate();
        this.quantity = pm.getQuantity();
    }

    public PrescriptionMedicamentDTO(String patientEmail, Medicament medicament, LocalDate expiryDate, int quantity) {
        this.patientEmail = patientEmail;
        this.medicament = medicament;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String email) {
        this.patientEmail = email;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
