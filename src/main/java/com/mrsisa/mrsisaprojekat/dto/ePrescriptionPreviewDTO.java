package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.ePrescription;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ePrescriptionPreviewDTO {
    private Long id;
    private LocalDate expiryDate;
    private Set<MedicamentInePrescriptionDTO> medicine;

    public ePrescriptionPreviewDTO() {}

    public ePrescriptionPreviewDTO(ePrescription ePrescription) {
        this.id = ePrescription.getId();
        this.expiryDate = ePrescription.getDate();
        this.medicine = new HashSet<>();

        for(PrescriptionMedicament pm : ePrescription.getPrescriptionMedicaments()) {
            this.medicine.add(new MedicamentInePrescriptionDTO(pm));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Set<MedicamentInePrescriptionDTO> getMedicine() {
        return medicine;
    }

    public void setMedicine(Set<MedicamentInePrescriptionDTO> medicine) {
        this.medicine = medicine;
    }
}
