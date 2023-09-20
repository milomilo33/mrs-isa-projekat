package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalDate;

public class SendRequestDTO {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String description;

    public SendRequestDTO(LocalDate dateFrom, LocalDate dateTo, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
    }

    public SendRequestDTO() {}

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
