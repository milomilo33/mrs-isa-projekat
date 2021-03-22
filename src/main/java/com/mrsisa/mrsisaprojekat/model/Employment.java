package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalTime;

public class Employment {
	
	private LocalTime workHourFrom;
	private LocalTime workHourTo;
	private String emailDermatologa;
	private Long idPharmacy;
	private boolean deleted;
	
	public Employment() {}

	public LocalTime getWorkHourFrom() {
		return workHourFrom;
	}

	public void setWorkHourFrom(LocalTime workHourFrom) {
		this.workHourFrom = workHourFrom;
	}

	public LocalTime getWorkHourTo() {
		return workHourTo;
	}

	public void setWorkHourTo(LocalTime workHourTo) {
		this.workHourTo = workHourTo;
	}

	public String getEmailDermatologa() {
		return emailDermatologa;
	}

	public void setEmailDermatologa(String emailDermatologa) {
		this.emailDermatologa = emailDermatologa;
	}

	public Long getIdPharmacy() {
		return idPharmacy;
	}

	public void setIdPharmacy(Long idPharmacy) {
		this.idPharmacy = idPharmacy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
