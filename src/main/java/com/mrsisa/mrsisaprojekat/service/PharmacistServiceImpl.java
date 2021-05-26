package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.AppointmentCalendarDTO;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacistRepositoryDB;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PharmacistServiceImpl  implements PharmacistService {

	@Autowired
	private PharmacistRepositoryDB pharmacistRepository;

	@Autowired
	private PatientService patientService;

	@Autowired
	private MedicalReportService medicalReportService;

	@Autowired
	private AppointmentRepositoryDB appointmentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	@Override
	public List<Pharmacist> findAll() {
		List<Pharmacist> pharmacists = pharmacistRepository.getAllWithAddress();
		return pharmacists;
	}

	@Override
	@Transactional
	public Pharmacist findOne(String email) {
		Pharmacist pharmacist = pharmacistRepository.getOnePharmacist(email);
		return pharmacist;
	}

	@Override
	public Pharmacist create(Pharmacist pharmacist) throws Exception {
		pharmacist.setPassword(passwordEncoder.encode(pharmacist.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_PHARMACIST");
		pharmacist.setRoles(roles);
		return pharmacistRepository.save(pharmacist);
	}

	@Override
	public Pharmacist update(Pharmacist pharmacist) throws Exception {
		Pharmacist pharmacistToUpdate = pharmacistRepository.findById(pharmacist.getEmail()).orElseGet(null);
		if (pharmacistToUpdate == null) {
			return null;
		}
		pharmacistRepository.save(pharmacist);
		return pharmacist;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(String email) {
		// potrebno je proveriti da li ima zakazana savetovanja, ukoliko ih ima ne moze da se obrise
		Pharmacist pharmacist = pharmacistRepository.getOnePharmacist(email);
		try {
			if(pharmacist.getCounselings().size()== 0) {	
				pharmacistRepository.deleteOne(email);
				return true;
			}
			return false;
		}catch(NullPointerException e) {
			return false;
		}
		
	}

	@Override
	public List<Appointment> getAvailableAppointments(Pharmacist p) {
		List<Appointment> availableAppointments = new ArrayList<>();
		Pharmacist pharmacist = pharmacistRepository.getPharmacistWithCounselings(p.getEmail());
		for(Appointment a : pharmacist.getCounselings()) {
			if(a.getPatient() == null) {
				availableAppointments.add(a);
			}
		}
		return availableAppointments;
	}

	@Override
	public Pharmacist findOneCounselings(String email) {
		Pharmacist p = pharmacistRepository.getPharmacistWithCounselings(email);

		return p;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getDoneCounselingsWithPatientsForPharmacist(String email) {
		Pharmacist pharmacist = this.findOne(email);

		if (pharmacist == null) {
			return null;
		}

		Collection<Appointment> counselings = pharmacist.getCounselings();
		Collection<Appointment> doneCounselings = new ArrayList<Appointment>();
		if (counselings == null) {
			return doneCounselings;
		}

		for (Appointment a : counselings) {
			if (a.isDone()) {
				a.setMedicalReport(null);
				a.setChosenEmployee(null);
				a.getPatient().setSubscribedPharmacies(null);
				a.getPatient().setAllergies(null);
				a.getPatient().setAppointments(null);
				a.getPatient().setComplaints(null);
				a.getPatient().setePrescriptions(null);
				a.getPatient().setReservedMedicaments(null);
				a.setPatient((Patient) Hibernate.unproxy(a.getPatient()));
				doneCounselings.add(a);
			}
		}

		return doneCounselings;
	}

	@Override
	public Pharmacist getRatings(String email) {
		return pharmacistRepository.getRatings(email);
	}

	@Override
	public void addRating(Rating rating, String ratedEmployeeEmail) {
		Pharmacist pharmacist = pharmacistRepository.loadWithRatingOfUser(ratedEmployeeEmail, rating.getPatient().getEmail());

		if (pharmacist == null) {
			pharmacist = pharmacistRepository.getRatings(ratedEmployeeEmail);
			try {
				pharmacist.getRatings().add(rating);
			} catch (NullPointerException e) {
				HashSet<Rating> ratings = new HashSet<>();
				ratings.add(rating);
				pharmacist.setRatings(ratings);
			}
		} else {
			pharmacist.getRatings().stream().findFirst().ifPresent(r -> r.setValue(rating.getValue()));
		}

		pharmacistRepository.save(pharmacist);

	}
	@Override
	public Integer getRatingOfUser(String pharmacistEmail, String patientEmail) {
		Pharmacist pharmacist = pharmacistRepository.loadWithRatingOfUser(pharmacistEmail, patientEmail);

		if(pharmacist == null) return 0;

		return Objects.requireNonNull(pharmacist.getRatings().stream().findFirst().orElse(null)).getValue();
	}
	public Pharmacist getOneWithAddress(String email) {
		Pharmacist pharmacist = pharmacistRepository.getOneWithAddress(email);
		if(pharmacist == null) {
			return null;
		}
		return pharmacist;
	}

	
	@Override
	public double getRating(String email) {
		Pharmacist d = pharmacistRepository.getRatings(email);
		try {
			d.getRatings();
		}catch(NullPointerException e) {
			return 0;
		}
			
			
		double val = 0;
			
		for(Rating g : d.getRatings()) {
			val+=g.getValue();
		}
		val= val/d.getRatings().size();
		return val;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<AppointmentCalendarDTO> getAllAppointmentsBetweenDatesForCalendar(LocalDateTime startDate, LocalDateTime endDate, String pharmacistEmail) {
		// provera da li je datum validan
		if (startDate.isAfter(endDate) || startDate.equals(endDate)) {
			return null;
		}

		Pharmacist pharmacist = this.findOne(pharmacistEmail);

		if (pharmacist == null) {
			return null;
		}

		if (pharmacist.isDeleted()) {
			return null;
		}

		Collection<Appointment> appointments = pharmacist.getCounselings();
		if (appointments == null) {
			return new HashSet<>();
		}
		appointments = appointments
				.stream()
				.filter(a -> {
					LocalDateTime appStartDate = a.getDate().atTime(a.getTermFrom());
					LocalDateTime appEndDate = a.getDate().atTime(a.getTermTo());
					return !a.isDeleted() && !appStartDate.isAfter(endDate) && !startDate.isAfter(appEndDate);
				})
				.collect(Collectors.toSet());

		Collection<AppointmentCalendarDTO> calendarAppointments = new HashSet<>();
		for (Appointment a : appointments) {
			Pharmacy pharmacy = pharmacist.getPharmacy();

			String pharmacyName = pharmacy.getName();
			String patientName = null;
			String patientLastName = null;
			if (a.getPatient() != null) {
				patientName = a.getPatient().getName();
				patientLastName = a.getPatient().getLastName();
			}
			Long appointmentId = a.getId();
			LocalDateTime appStartDate = a.getDate().atTime(a.getTermFrom());
			LocalDateTime appEndDate = a.getDate().atTime(a.getTermTo());
			String appStartDateStr = appStartDate.format(DateTimeFormatter.ISO_DATE_TIME);
			String appEndDateStr = appEndDate.format(DateTimeFormatter.ISO_DATE_TIME);
			String status = null;
			if (patientName == null) {
				status = "slot";
			}
			else if (a.isDone()) {
				status = "done";
			}
			else {
				if (a.getMedicalReport() != null) {
					status = "started";
				}
				else {
					status = "scheduled";
				}
			}
			AppointmentCalendarDTO appDto = new AppointmentCalendarDTO(patientName, patientLastName, appointmentId, appStartDateStr, appEndDateStr, pharmacyName, status);
			calendarAppointments.add(appDto);
		}

		return calendarAppointments;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getUpcomingCounselingsForPharmacist(String email) {
		Pharmacist pharmacist = this.findOne(email);

		if (pharmacist == null) {
			return null;
		}

		Collection<Appointment> counselings = pharmacist.getCounselings();

		Collection<Appointment> upcomingAppointments = new ArrayList<Appointment>();
		for (Appointment a : counselings) {
			LocalDate appointmentDate = a.getDate();
			LocalDate today = LocalDate.now();
			if (!a.isDeleted() && !a.isDone() && a.getPatient() != null) {
				if (today.isBefore(appointmentDate) || today.isEqual(appointmentDate)) {
					a.setMedicalReport(null);
					a.setChosenEmployee(null);
					a.getPatient().setSubscribedPharmacies(null);
					a.getPatient().setAllergies(null);
					a.getPatient().setAppointments(null);
					a.getPatient().setComplaints(null);
					a.getPatient().setePrescriptions(null);
					a.getPatient().setReservedMedicaments(null);
					a.setPatient((Patient) Hibernate.unproxy(a.getPatient()));
					upcomingAppointments.add(a);
				}
			}
		}

		return upcomingAppointments;
	}

	@Override
	@Transactional
	public boolean pharmacistHasAppointment(String email, Long appointmentId) {
		Pharmacist pharmacist = this.findOne(email);

		if (pharmacist == null) {
			return false;
		}

		for (Appointment a : pharmacist.getCounselings()) {
			if (a.getId().equals(appointmentId)) {
				return true;
			}
		}

		return false;
	}

	@Override
	@Transactional
	public String createAndScheduleNewAppointment(String pharmacistEmail, String patientEmail, LocalDate date, LocalTime timeFrom, LocalTime timeTo, Long medicalReportId) {
		// provera da li je datum validan
		if (timeFrom.isAfter(timeTo) || timeFrom.equals(timeTo)) {
			return "Invalid date!";
		}
		LocalDate today = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		if (date.isBefore(today) || (date.isEqual(today) && timeFrom.isBefore(timeNow))) {
			return "Invalid date!";
		}

		Pharmacist pharmacist = this.findOne(pharmacistEmail);
		Patient patient = patientService.findOne(patientEmail);
		MedicalReport report = medicalReportService.findOne(medicalReportId);

		if (patient == null || pharmacist == null) {
			return "Invalid pharmacist or patient!";
		}

		if (patient.isDeleted() || pharmacist.isDeleted()) {
			return "Invalid pharmacist or patient!";
		}

		if (report == null) {
			return "Medical report does not exist!";
		}

		if (report.isDeleted()) {
			return "Medical report does not exist!";
		}

		Pharmacy pharmacy = pharmacist.getPharmacy();

		if (pharmacist.getWorkHour() == null) {
			return "Pharmacist does not have work hours!";
		}

		// provera da li je termin u okviru radnog vremena farmaceuta
		Set<WorkHour> workHoursInPharmacy = pharmacist.getWorkHour()
				.stream()
				.filter(wh -> !wh.isDeleted() && wh.getPharmacy().getId().equals(pharmacy.getId()))
				.collect(Collectors.toSet());
		boolean appointmentDuringWorkHours = false;
		int dayInt = date.getDayOfWeek().getValue();
		for (WorkHour wh : workHoursInPharmacy) {
			LocalTime workHourFrom = wh.getWorkHourFrom();
			LocalTime workHourTo = wh.getWorkHourTo();
			// + 1 -- zato sto dayOfWeek pocinje od 1
			int workHourDayInt = wh.getDay().ordinal() + 1;
			if (workHourDayInt == dayInt && (workHourFrom.isBefore(timeFrom) || workHourFrom.equals(timeFrom)) &&
					(workHourTo.isAfter(timeTo) || workHourTo.equals(timeTo))) {
				appointmentDuringWorkHours = true;
				break;
			}
		}

		if (!appointmentDuringWorkHours) {
			return "Appointment is not during pharmacist's working hours!";
		}

		// provera da li se termin preklapa sa postojecim terminom farmaceuta
		if (pharmacist.getCounselings() != null) {
			for (Appointment a : pharmacist.getCounselings()) {
				if (!a.isDeleted()) {
					// preklapanje
					if (a.getDate().isEqual(date) && a.getTermFrom().isBefore(timeTo) && timeFrom.isBefore(a.getTermTo())) {
						return "Appointment overlaps with another one of your appointments!";
					}
				}
			}
		}

		// provera da li je termin u okviru neradnih dana farmaceuta
		if (pharmacist.getCalendar() != null) {
			for (Vacation v : pharmacist.getCalendar().getVacations()) {
				if (!v.isDeleted()) {
					if (date.isEqual(v.getDateFrom()) || date.isEqual(v.getDateTo()) || (date.isAfter(v.getDateFrom()) && date.isBefore(v.getDateTo()))) {
						return "Appointment overlaps with vacation days!";
					}
				}
			}
		}

		// provera da li se termin preklapa sa postojecim pregledom pacijenta
		if (patient.getAppointments() != null) {
			for (Appointment a : patient.getAppointments()) {
				if (!a.isDeleted()) {
					if (a.getDate().isEqual(date) && a.getTermFrom().isBefore(timeTo) && timeFrom.isBefore(a.getTermTo())) {
						return "Appointment overlaps with another one of the patient's appointments!";
					}
				}
			}
		}

		Appointment newAppointment = new Appointment();
		newAppointment.setDeleted(false);
		newAppointment.setPatient(patient);
		newAppointment.setChosenEmployee(pharmacist);
		newAppointment.setType(Appointment.AppointmentType.COUNSELING);
		newAppointment.setDate(date);
		newAppointment.setTermFrom(timeFrom);
		newAppointment.setTermTo(timeTo);

		appointmentRepository.save(newAppointment);

		pharmacy.getAppointments().add(newAppointment);

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getAllExistingCounselingsForPharmacist(String email) {
		Pharmacist pharmacist = this.findOne(email);

		if (pharmacist == null) {
			return null;
		}

		Collection<Appointment> counselings = pharmacist.getCounselings();
		Collection<Appointment> existingCounselings = new ArrayList<Appointment>();
		if (pharmacist == null) {
			return existingCounselings;
		}

		LocalDate today = LocalDate.now();
		for (Appointment a : counselings) {
			LocalDate appointmentDate = a.getDate();
			if (a.getPatient() == null) {
				boolean isBeforeScheduledTime = false;
				LocalTime timeFrom = a.getTermFrom();
				LocalTime now = LocalTime.now();
				isBeforeScheduledTime = now.isBefore(timeFrom);
				if (today.isBefore(appointmentDate) || (today.isEqual(appointmentDate) && isBeforeScheduledTime)) {
					a.setMedicalReport(null);
					a.setChosenEmployee(null);
					existingCounselings.add(a);
				}
			}
		}

		return existingCounselings;
	}

}
