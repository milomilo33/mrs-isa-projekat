package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.AppointmentCalendarDTO;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.DermatologistRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DermatologistServiceImpl implements DermatologistService {

	@Autowired
	private DermatologistRepositoryDB dermatologistRepository;

	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;

	@Autowired
	private AppointmentRepositoryDB appointmentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private MedicalReportService medicalReportService;

	@Override
	public List<Dermatologist> findAll() {
		List<Dermatologist> dermatologists = dermatologistRepository.getAllWithAddress();
		return dermatologists;
	}

	@Override
	public Dermatologist findOne(String email) {
		Dermatologist dermatologist = dermatologistRepository.getOneDermatologist(email);
		return dermatologist;
	}

	@Override
	public Dermatologist create(Dermatologist dermatologist) throws Exception {
		dermatologist.setPassword(passwordEncoder.encode(dermatologist.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_DERMATOLOGIST");
		dermatologist.setRoles(roles);
		return dermatologistRepository.save(dermatologist);
	}

	@Override
	public Dermatologist update(Dermatologist dermatologist) throws Exception {
		Dermatologist dermatologistToUpdate = dermatologistRepository.findById(dermatologist.getEmail()).orElseGet(null);
		if (dermatologistToUpdate == null) {
			return null;
		}
		dermatologistRepository.save(dermatologist);
		return dermatologist;
	}

	@Override
	public boolean delete(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getUpcomingExaminationsForDermatologist(String email) {
		Dermatologist dermatologist = this.findOne(email);

		if (dermatologist == null) {
			return null;
		}

		Collection<Appointment> examinations = dermatologist.getMedicalExaminations();

		Collection<Appointment> upcomingAppointments = new ArrayList<Appointment>();
		for (Appointment a : examinations) {
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
	public boolean dermatologistHasAppointment(String email, Long appointmentId) {
		Dermatologist dermatologist = this.findOne(email);

		if (dermatologist == null) {
			return false;
		}

		for (Appointment a : dermatologist.getMedicalExaminations()) {
			if (a.getId().equals(appointmentId)) {
				return true;
			}
		}

		return false;
	}


	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getDoneExaminationsWithPatientsForDermatologist(String email) {
		Dermatologist dermatologist = this.findOne(email);

		if (dermatologist == null) {
			return null;
		}

		Collection<Appointment> examinations = dermatologist.getMedicalExaminations();
		Collection<Appointment> doneExaminations = new ArrayList<Appointment>();
		if (examinations == null) {
			return doneExaminations;
		}

		for (Appointment a : examinations) {
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
				doneExaminations.add(a);
			}
		}

		return doneExaminations;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Appointment> getAllExistingExaminationsForDermatologist(String email) {
		Dermatologist dermatologist = this.findOne(email);

		if (dermatologist == null) {
			return null;
		}

		Collection<Appointment> examinations = dermatologist.getMedicalExaminations();
		Collection<Appointment> existingExaminations = new ArrayList<Appointment>();
		if (examinations == null) {
			return existingExaminations;
		}

		LocalDate today = LocalDate.now();
		for (Appointment a : examinations) {
			LocalDate appointmentDate = a.getDate();
			if (a.getPatient() == null) {
				boolean isBeforeScheduledTime = false;
				LocalTime timeFrom = a.getTermFrom();
				LocalTime now = LocalTime.now();
				isBeforeScheduledTime = now.isBefore(timeFrom);
				if (today.isBefore(appointmentDate) || (today.isEqual(appointmentDate) && isBeforeScheduledTime)) {
					a.setMedicalReport(null);
					a.setChosenEmployee(null);
					existingExaminations.add(a);
				}
			}
		}

		return existingExaminations;
	}

	@Override
	public Dermatologist getRatings(String email) {
		return dermatologistRepository.getRatings(email);
	}

	@Override
	public void addRating(Rating rating, String ratedEmployeeEmail) {
		Dermatologist dermatologist = dermatologistRepository.loadWithRatingOfUser(ratedEmployeeEmail, rating.getPatient().getEmail());

		if (dermatologist == null) {
			dermatologist = dermatologistRepository.getRatings(ratedEmployeeEmail);
			try {
				dermatologist.getRatings().add(rating);
			} catch (NullPointerException e) {
				HashSet<Rating> ratings = new HashSet<>();
				ratings.add(rating);
				dermatologist.setRatings(ratings);
			}
		} else {
			dermatologist.getRatings().stream().findFirst().ifPresent(r -> r.setValue(rating.getValue()));
		}

		dermatologistRepository.save(dermatologist);

	}

	@Override
	public Integer getRatingOfUser(String dermatologistEmail, String patientEmail) {
		Dermatologist dermatologist = dermatologistRepository.loadWithRatingOfUser(dermatologistEmail, patientEmail);

		if (dermatologist == null) return 0;

		return Objects.requireNonNull(dermatologist.getRatings().stream().findFirst().orElse(null)).getValue();
	}

	@Override
	public Dermatologist getOneWithAddress(String email) {
		Dermatologist dermatologist = dermatologistRepository.getOneWithAddress(email);
		if(dermatologist == null) {
			return null;
		}
		return dermatologist;
	}

	public double getRating(String email) {
		Dermatologist d = dermatologistRepository.getRatings(email);
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
	public Dermatologist findOneExaminations(String email) {
		Dermatologist d = dermatologistRepository.getDermatologistWithExaminations(email);

		return d;
	}

	@Override
	@Transactional
	public String createAndScheduleNewAppointment(String dermatologistEmail, String patientEmail, LocalDate date, LocalTime timeFrom, LocalTime timeTo, Long medicalReportId) {
		// provera da li je datum validan
		if (timeFrom.isAfter(timeTo) || timeFrom.equals(timeTo)) {
			return "Invalid date!";
		}
		LocalDate today = LocalDate.now();
		LocalTime timeNow = LocalTime.now();
		if (date.isBefore(today) || (date.isEqual(today) && timeFrom.isBefore(timeNow))) {
			return "Invalid date!";
		}

		Dermatologist dermatologist = this.findOne(dermatologistEmail);
		Patient patient = patientService.findOne(patientEmail);
		MedicalReport report = medicalReportService.findOne(medicalReportId);

		if (patient == null || dermatologist == null) {
			return "Invalid dermatologist or patient!";
		}

		if (patient.isDeleted() || dermatologist.isDeleted()) {
			return "Invalid dermatologist or patient!";
		}

		if (report == null) {
			return "Medical report does not exist!";
		}

		if (report.isDeleted()) {
			return "Medical report does not exist!";
		}

		Pharmacy pharmacy = report.getEprescription().getPharmacy();

		if (pharmacy == null) {
			return "Pharmacy does not exist!";
		}

		boolean dermatologistInPharmacy = false;
		for (Dermatologist d : pharmacy.getDermatologists()) {
			if (d.getEmail().equals(dermatologistEmail)) {
				dermatologistInPharmacy = true;
			}
		}
		if (!dermatologistInPharmacy) {
			return "Dermatologist does not work in this pharmacy!";
		}

		boolean appointmentMeetsCriteria = true;
		if (dermatologist.getWorkHour() == null) {
			return "Dermatologist does not have work hours!";
		}

		// provera da li je termin u okviru radnog vremena dermatologa
		Set<WorkHour> workHoursInPharmacy = dermatologist.getWorkHour()
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
			}
		}

		if (!appointmentDuringWorkHours) {
			return "Appointment is not during dermatologist's working hours!";
		}

		// provera da li se termin preklapa sa postojecim terminom dermatologa
		if (dermatologist.getMedicalExaminations() != null) {
			for (Appointment a : dermatologist.getMedicalExaminations()) {
				if (!a.isDeleted()) {
					// preklapanje
					if (a.getDate().isEqual(date) && a.getTermFrom().isBefore(timeTo) && timeFrom.isBefore(a.getTermTo())) {
						return "Appointment overlaps with another one of your appointments!";
					}
				}
			}
		}

		// provera da li je termin u okviru neradnih dana dermatologa
		if (dermatologist.getCalendar() != null) {
			for (Vacation v : dermatologist.getCalendar().getVacations()) {
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
		newAppointment.setChosenEmployee(dermatologist);
		newAppointment.setType(Appointment.AppointmentType.EXAMINATION);
		newAppointment.setDate(date);
		newAppointment.setTermFrom(timeFrom);
		newAppointment.setTermTo(timeTo);

		appointmentRepository.save(newAppointment);

		pharmacy.getAppointments().add(newAppointment);

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<AppointmentCalendarDTO> getAllAppointmentsBetweenDatesForCalendar(LocalDateTime startDate, LocalDateTime endDate, String dermatologistEmail) {
		// provera da li je datum validan
		if (startDate.isAfter(endDate) || startDate.equals(endDate)) {
			return null;
		}

		Dermatologist dermatologist = this.findOne(dermatologistEmail);

		if (dermatologist == null) {
			return null;
		}

		if (dermatologist.isDeleted()) {
			return null;
		}

		Collection<Appointment> appointments = dermatologist.getMedicalExaminations();
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
			Set<Pharmacy> pharmacies = dermatologist.getPharmacies();
			String pharmacyName = null;
			for (Pharmacy p : pharmacies) {
				boolean pharmacyFound = false;
				for (Appointment appPharmacy : p.getAppointments()) {
					if (appPharmacy.getId().equals(a.getId())) {
						pharmacyName = p.getName();
						pharmacyFound = true;
						break;
					}
				}
				if (pharmacyFound) {
					break;
				}
			}

			if (pharmacyName == null) {
				return null;
			}

			String patientName = a.getPatient().getName();
			String patientLastName = a.getPatient().getLastName();
			Long appointmentId = a.getId();
			LocalDateTime appStartDate = a.getDate().atTime(a.getTermFrom());
			LocalDateTime appEndDate = a.getDate().atTime(a.getTermTo());
			AppointmentCalendarDTO appDto = new AppointmentCalendarDTO(patientName, patientLastName, appointmentId, appStartDate, appEndDate, pharmacyName);
			calendarAppointments.add(appDto);
		}

		return calendarAppointments;
	}
}
