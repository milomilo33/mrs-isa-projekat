package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.AppointmentCalendarDTO;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Rating;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface PharmacistService {
	
	List<Pharmacist> findAll();
	
	Pharmacist findOne(String email);
	
	Pharmacist create(Pharmacist pharmacist) throws Exception;
	
	Pharmacist update(Pharmacist pharmacist) throws Exception;
	
	boolean delete(String email);

	List<Appointment> getAvailableAppointments(Pharmacist p);

	Pharmacist findOneCounselings(String email);

	Collection<Appointment> getDoneCounselingsWithPatientsForPharmacist(String email);
	
	Pharmacist getRatings(String email);

	void addRating(Rating rating, String ratedEmployeeEmail);

	Integer getRatingOfUser(String pharmacistEmail, String patientEmail);
  
	Pharmacist getOneWithAddress(String email);
	
	double getRating(String email);

	Collection<AppointmentCalendarDTO> getAllAppointmentsBetweenDatesForCalendar(LocalDateTime startDate, LocalDateTime endDate, String dermatologistEmail);

	Collection<Appointment> getUpcomingCounselingsForPharmacist(String email);

	boolean pharmacistHasAppointment(String email, Long appointmentId);

	String createAndScheduleNewAppointment(String pharmacistEmail, String patientEmail, LocalDate date, LocalTime timeFrom, LocalTime timeTo, Long medicalReportId);

	Collection<Appointment> getAllExistingCounselingsForPharmacist(String email);
}
