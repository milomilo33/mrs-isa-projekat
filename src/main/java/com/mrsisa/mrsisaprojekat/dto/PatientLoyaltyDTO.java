package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Category;

public class PatientLoyaltyDTO {
    private Integer points;
    private Category category;

    public PatientLoyaltyDTO() {
    }

    public PatientLoyaltyDTO(Integer points, Category category) {
        this.points = points;
        this.category = category;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
