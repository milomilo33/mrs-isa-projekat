package com.mrsisa.mrsisaprojekat.dto;

import java.time.LocalTime;
import com.mrsisa.mrsisaprojekat.model.WorkHour;

public class WorkHourDTO {
	
	private Long id;
	private String day;
	private LocalTime workHourFrom;
	private LocalTime workHourTo;
	private PharmacyDTO pharmacy;
	private boolean deleted;
	
	public WorkHourDTO() {}
	public WorkHourDTO(WorkHour workHour) {
		this(workHour.getId(), workHour.getDay().name().toLowerCase(),workHour.getWorkHourFrom(), workHour.getWorkHourTo(), new PharmacyDTO(workHour.getPharmacy()),workHour.isDeleted());
		
	}
	
	public WorkHourDTO(Long id,String day,LocalTime workHourFrom, LocalTime workHourTo,PharmacyDTO pharmacy, boolean deleted) {
		super();
		this.id = id;
		this.day = day;
		this.workHourFrom = workHourFrom;
		this.workHourTo = workHourTo;
		this.pharmacy = pharmacy;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public String getDay() {
		return day;
	}

	public LocalTime getWorkHourFrom() {
		return workHourFrom;
	}

	public LocalTime getWorkHourTo() {
		return workHourTo;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	

}
