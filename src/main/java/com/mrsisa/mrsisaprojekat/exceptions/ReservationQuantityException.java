package com.mrsisa.mrsisaprojekat.exceptions;

public class ReservationQuantityException extends Exception {
    private Integer quantity;
    public ReservationQuantityException(Integer quantity, String message) {
        super(message);
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
