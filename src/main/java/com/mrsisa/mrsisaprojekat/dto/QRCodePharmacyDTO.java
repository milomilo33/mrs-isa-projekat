package com.mrsisa.mrsisaprojekat.dto;

import java.util.Set;

public class QRCodePharmacyDTO {

	private Set<PharmacyDTO> pharmacySet;
	private PharmacyDTO pharmacy;
	private String datePhurchased;
	private QRCodeDTO qrCode;
	
	public QRCodePharmacyDTO() {}
	
	public QRCodePharmacyDTO(QRCodeDTO qrCode, Set<PharmacyDTO> pharmacySet) {
		this.qrCode = qrCode;
		this.pharmacySet = pharmacySet;
	}
	
	public QRCodePharmacyDTO(PharmacyDTO pharmacy, String datePhurchased, QRCodeDTO qrCode) {
		this.datePhurchased = datePhurchased;
		this.pharmacy = pharmacy;
		this.qrCode = qrCode;
	}

	public Set<PharmacyDTO> getPharmacySet() {
		return pharmacySet;
	}

	public void setPharmacySet(Set<PharmacyDTO> pharmacySet) {
		this.pharmacySet = pharmacySet;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}

	public String getDatePhurchased() {
		return datePhurchased;
	}

	public void setDatePhurchased(String datePhurchased) {
		this.datePhurchased = datePhurchased;
	}

	public QRCodeDTO getQrCode() {
		return qrCode;
	}

	public void setQrCode(QRCodeDTO qrCode) {
		this.qrCode = qrCode;
	}
	
	
}
