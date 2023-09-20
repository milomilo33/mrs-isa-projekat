package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

public class ListPharmacyDTO {
    private Long id;
    private String name;
    private String description;
    private AddressDTO adress;

    public ListPharmacyDTO() {
    }

    public ListPharmacyDTO(Long id, String name, String description, AddressDTO adress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.adress = adress;
    }

    public ListPharmacyDTO(Pharmacy p) {
        this(p.getId(), p.getName(), p.getDescription(), new AddressDTO(p.getAddress()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressDTO getAdress() {
        return adress;
    }

    public void setAdress(AddressDTO adress) {
        this.adress = adress;
    }
}
