package com.mrsisa.mrsisaprojekat.dto;



public class RatingDTO {
    public enum RateType {
        PHARMACY, MEDICAMENT, PHARMACIST, DERMATOLOGIST
    }

    private RateType rateType;
    private Long ratedEntityId;
    private String ratedEmployeeEmail;
    private String patientEmail;
    private Integer rating;

    public RatingDTO() {

    }

    public RatingDTO(RateType rateType, String ratedEmployeeEmail, String patientEmail, Integer rating) {
        this.rateType = rateType;
        this.ratedEntityId = null;
        this.ratedEmployeeEmail = ratedEmployeeEmail;
        this.patientEmail = patientEmail;
        this.rating = rating;
    }

    public RatingDTO(RateType rateType, Long ratedEntityId, String patientEmail, Integer rating) {
        this.rateType = rateType;
        this.ratedEntityId = ratedEntityId;
        this.ratedEmployeeEmail = null;
        this.patientEmail = patientEmail;
        this.rating = rating;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public Long getRatedEntityId() {
        return ratedEntityId;
    }

    public void setRatedEntityId(Long ratedEntityId) {
        this.ratedEntityId = ratedEntityId;
    }

    public String getRatedEmployeeEmail() {
        return ratedEmployeeEmail;
    }

    public void setRatedEmployeeEmail(String ratedEmployeeEmail) {
        this.ratedEmployeeEmail = ratedEmployeeEmail;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
