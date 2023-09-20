package com.mrsisa.mrsisaprojekat.dto;

import java.util.Map;

public class AppointmentDetailsDTO {
    private String text;
    private Map<String, Integer> medicineQuantity;

    public AppointmentDetailsDTO(String text, Map<String, Integer> medicineQuantity) {
        this.text = text;
        this.medicineQuantity = medicineQuantity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Integer> getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(Map<String, Integer> medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }
}
