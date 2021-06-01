package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.AppointmentCalendarDTO;
import com.mrsisa.mrsisaprojekat.exceptions.RatingException;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Rating;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
public interface DermatologistService {
	List<Dermatologist> findAll();
	
	Dermatologist findOne(String email);
	
	Dermatologist create(Dermatologist dermatologist) throws Exception;
	
	Dermatologist update(Dermatologist dermatologist) throws Exception;
	
	void delete(String email);

	Collection<Appointment> getUpcomingExaminationsForDermatologist(String email);

	Appointment getUpcomingExaminationForDermatologist(String email, Long appointmentId);

	boolean dermatologistHasAppointment(String email, Long appointmentId);


	Collection<Appointment> getDoneExaminationsWithPatientsForDermatologist(String email);

	Collection<Appointment> getAllExistingExaminationsForDermatologist(String email);
	
	Dermatologist getRatings(String email);

	void addRating(Rating rating, String ratedEmployeeEmail) throws RatingException;

	Integer getRatingOfUser(String dermatologistEmail, String patientEmail);
  
	Dermatologist getOneWithAddress(String email);

	Dermatologist findOneExaminations(String email);

	String createAndScheduleNewAppointment(String dermatologistEmail, String patientEmail, LocalDate date, LocalTime timeFrom, LocalTime timeTo, Long medicalReportId);
	
	double getRating(String email);

	Collection<AppointmentCalendarDTO> getAllAppointmentsBetweenDatesForCalendar(LocalDateTime startDate, LocalDateTime endDate, String dermatologistEmail);
}
