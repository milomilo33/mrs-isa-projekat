package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;

public class MedicamentInePrescriptionDTO {
    private String name;
    private Integer quantity;

    public MedicamentInePrescriptionDTO() {}

    public MedicamentInePrescriptionDTO(PrescriptionMedicament prescriptionMedicament) {
        this.name = prescriptionMedicament.getMedicament().getName();
        this.quantity = prescriptionMedicament.getQuantity();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
