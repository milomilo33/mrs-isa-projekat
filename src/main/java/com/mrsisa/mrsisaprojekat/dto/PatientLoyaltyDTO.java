package com.mrsisa.mrsisaprojekat.dto;

public class PatientLoyaltyDTO {
    private Integer points;
    private String category;

    public PatientLoyaltyDTO() {
    }

    public PatientLoyaltyDTO(Integer points, String category) {
        this.points = points;
        this.category = category;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
