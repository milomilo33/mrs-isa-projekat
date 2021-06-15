package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.exceptions.ReservationQuantityException;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepositoryDB patientRepository;

	@Autowired
	private PrescriptionRepositoryDB prescriptionRepository;

	@Autowired
	private MedicamentItemRepositoryDB medicamentItemRepository;

	@Autowired
	private AppointmentRepositoryDB appointmentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MedicamentRepositoryDB medicamentRepository;

	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;

	@Autowired
	private ePrescriptionRepositoryDB ePrescriptionRepository;

	@Autowired
	private MedicalReportService medicalReportService;

	@Autowired
	private RequestMedicamentRepositoryDB requestMedicamentRepository;

	@Override
	public Collection<Patient> findAll() {
		List<Patient> patients = patientRepository.getAllWithAddress();
		return patients;
	}

	@Override
	public Patient findOne(String id) {
		return patientRepository.findById(id).orElse(null);
	}

	@Override
	public Patient create(Patient patient) throws Exception {

		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		List<Role> roles = roleService.findByName("ROLE_PATIENT");
		patient.setRoles(roles);
		return patientRepository.save(patient);
	}

	@Override
	public Patient update(Patient patient) throws Exception {
		Patient patientToUpdate = patientRepository.findById(patient.getEmail()).orElseGet(null);
		if (patientToUpdate == null) {
			return null;
		}
		patientToUpdate.setePrescriptions(patient.getePrescriptions());
		return patientRepository.save(patient);
	}

	@Override
	public Patient getOneWithReservedMeds(String email) {
		Patient p = patientRepository.getPatientWithReservedMedicaments(email);
		p.setAppointments(new HashSet<>());
		p.setComplaints(new HashSet<>());
		p.setAllergies(new HashSet<>());
		p.setePrescriptions(new HashSet<>());
		p.setSubscribedPharmacies(new HashSet<>());
		if (p == null) {
			p = findOne(email);
			p.setReservedMedicaments(new HashSet<>());
			p.setAppointments(new HashSet<>());
			p.setComplaints(new HashSet<>());
			p.setAllergies(new HashSet<>());
			p.setePrescriptions(new HashSet<>());
			p.setSubscribedPharmacies(new HashSet<>());
		}
		return p;
	}

	@Override
	@Transactional(readOnly = true)
	public Patient getOneWithReservedMedsAndePrescriptions(String email) {
		Patient p = patientRepository.getPatientWithReservedMedicaments(email);
		if (p == null) {
			p = findOne(email);
			if (p == null) {
				return null;
			}
			p.setReservedMedicaments(new HashSet<>());
		}
		if (p.getePrescriptions() == null) {
			p.setePrescriptions(new HashSet<>());
		}
		//Hibernate.initialize(p.getePrescriptions());
		return p;
	}

	@Override
	@Transactional
	public PrescriptionMedicament updateWithReservation(Patient p, PrescriptionMedicament medicamentToReserve) {
		Patient patientToUpdate = patientRepository.getPatientWithReservedMedicaments(p.getEmail());

		if (patientToUpdate == null) {
			patientToUpdate = patientRepository.findById(p.getEmail()).orElse(null);
			if (patientToUpdate == null) return null;
			patientToUpdate.setReservedMedicaments(new HashSet<>());

		}

		patientToUpdate.getReservedMedicaments().add(medicamentToReserve);
		PrescriptionMedicament pm = prescriptionRepository.save(medicamentToReserve);
		patientRepository.save(p);

		prescriptionRepository.updatePatientReservation(p.getEmail(), medicamentToReserve.getId());

		return medicamentToReserve;
	}

	@Override
	@Transactional
	public PrescriptionMedicament updateWithPrescription(String patientEmail, PrescriptionMedicament medicamentToReserve, Long medicalReportId) throws Exception {
		Patient patientToUpdate = this.getOneWithReservedMedsAndePrescriptions(patientEmail);

		if (patientToUpdate == null) {
			return null;
		}

		MedicalReport report = medicalReportService.findOne(medicalReportId);

		if (report == null) {
			return null;
		}

		report.getEprescription().getPrescriptionMedicaments().add(medicamentToReserve);

		medicamentToReserve.setExpiryDate(report.getEprescription().getDate());
		prescriptionRepository.save(medicamentToReserve);

		patientToUpdate.getReservedMedicaments().add(medicamentToReserve);

		return medicamentToReserve;
	}


	@Override
	public void delete(String id) {
		patientRepository.deleteById(id);
	}


	@Override
	//@Transactional
	public Collection<Patient> findByNameAndLastName(String name, String lastName) {
		return patientRepository.findByNameAndLastName(name, lastName);
	}

	@Override
	public Patient findAllWithPurchasedMedicamentsAndAppointments(String email) {
		return patientRepository.getPatientWithPurchasedMedicaments(email);
	}

	@Override
	public Patient getOneWithAddress(String email) {
		return patientRepository.getOneWithAddress(email);
	}

	@Override
	public Collection<Appointment> getUpcomingAppointmentsForUser(String userEmail, Appointment.AppointmentType type) {
//		List<Appointment> appointments = patientRepository.getAppointmentsForUser(userEmail);
//
//		List<Appointment> upcomingAppointments = new ArrayList<Appointment>();
//		for (Appointment a : appointments) {
//			LocalDate appointmentDate = a.getDate();
//			LocalDate today = LocalDate.now();
//			if (today.isBefore(appointmentDate) || today.isEqual(appointmentDate)) {
//				if (a.getType() == type) {
//					a.setMedicalReport(null);
//					upcomingAppointments.add(a);
//				}
//			}

		//	}

		return null;
	}

	@Override
	public Patient getOneWithAppointments(String email) {
		Patient p = patientRepository.getAppointmentsForUser(email);

		if (p == null) {
			p = findOne(email);
			p.setAppointments(new HashSet<>());
		}
		return p;
	}

	@Override
	public Long updateWithAppointment(Patient patient, Appointment appointment) {
		patient.getAppointments().add(appointment);
		Appointment a = appointmentRepository.save(appointment);
//		patientRepository.save(patient);

		return a.getId();
	}

	@Override
	public Patient getPatientDetails(String email) {
		Patient patient = patientRepository.getOne(email);
		return patient;

	}

	@Override
	public void checkMedicamentReservationQuantity(PrescriptionMedicament medicament, Long pharmacyId) throws ReservationQuantityException {
		Pharmacy pharmacy = pharmacyRepository.findMedicamentItem(pharmacyId);

		MedicamentItem medicamentItem = pharmacy.getMedicamentItems().stream().filter(m -> m.getMedicament().getId()
				.equals(medicament.getMedicament().getId())).findFirst().orElse(null);

		try {
			if (medicamentItem != null) {
				if (medicamentItem.getQuantity() - medicament.getQuantity() < 0) {
					try {
						throw new ReservationQuantityException(medicamentItem.getQuantity(), "Not enough chosen medicament");
					} catch (ReservationQuantityException e) {
						e.printStackTrace();
					}
				} else {
					//medicamentItem.setQuantity(medicamentItem.getQuantity() - medicament.getQuantity());
					medicamentItem.setQuantity(medicamentItem.getQuantity() - medicament.getQuantity());
					medicamentItemRepository.save(medicamentItem);


//				medicamentItem.setQuantity(medicamentItem.getQuantity() - medicament.getQuantity());
//				medicamentItemRepository.save(medicamentItem);


				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	@Transactional
	public boolean checkMedicamentReservationQuantityForPrescription(PrescriptionMedicament medicament, Long pharmacyId, Employee employee) throws ReservationQuantityException {
		Pharmacy pharmacy = pharmacyRepository.findMedicamentItem(pharmacyId);

		MedicamentItem medicamentItem = pharmacy.getMedicamentItems().stream().filter(m -> m.getMedicament().getId()
				.equals(medicament.getMedicament().getId())).findFirst().orElse(null);

		if (medicamentItem != null) {
			if (medicamentItem.getQuantity() - medicament.getQuantity() < 0) {
				for (AdminPharmacy admin : pharmacy.getAdmins()) {
					// check persistence
					RequestMedicament requestMedicament = new RequestMedicament();
					requestMedicamentRepository.save(requestMedicament);
					requestMedicament.setAccepted(false);
					requestMedicament.setAdmin(admin);
					requestMedicament.setEmployee(employee);
					requestMedicament.setMedicament(medicament.getMedicament());
					requestMedicament.setQuantity(medicament.getQuantity());
					admin.getRequestMedicaments().add(requestMedicament);
				}

				throw new ReservationQuantityException(medicamentItem.getQuantity(), "Not enough chosen medicament");
			} else {
				medicamentItem.setQuantity(medicamentItem.getQuantity() - medicament.getQuantity());
				medicamentItemRepository.save(medicamentItem);

				return true;
			}
		} else
			return false;
	}

	@Override
	public Collection<Pharmacy> findAllSubscribed(String user) {
		return patientRepository.findAllSubscribedPharmacies(user);
	}

	@Override
	public Collection<Pharmacy> filterPharmacy(String user, int rating) {
		Collection<Pharmacy> pharmacies;
		if(!user.equals("null")) {
			pharmacies = patientRepository.findAllSubscribedPharmacies(user);
		} else {
			pharmacies = pharmacyRepository.findAll();
		}

		Collection<Pharmacy> retval = new ArrayList<>();

		int avgRating;

		List<Rating> ratings;

		if(rating != -1) {
			for(Pharmacy p : pharmacies) {
				ratings = pharmacyRepository.getRatings(p.getId());
				int i = 0;
				int sum = 0;

				for(Rating r : ratings) {
					sum += r.getValue();
					i++;
				}
				if(i == 0) avgRating = 0;


				avgRating = Math.round(sum / i);

				if(avgRating >= rating) {
					System.out.println("OOOOOOOOO " + avgRating);
					retval.add(p);
				}
			}
		} else {
			retval = pharmacies;
		}

		return retval;
	}

	@Override
	public void addAllergy(String patientEmail, Long medicamentId) throws Exception {
		Patient p = patientRepository.getPatientWithAllergies(patientEmail);

		Medicament m = medicamentRepository.findById(medicamentId).orElse(null);

		if(p == null) {
			throw new Exception("Patient not found");
		}
		if(m == null) {
			throw new Exception("Medicament not found");
		}

		for(Medicament medicament : p.getAllergies()) {
			if(medicament.getId().equals(medicamentId)) {
				throw new Exception("Allergy already exists");
			}
		}
		p.getAllergies().add(m);
		patientRepository.save(p);


	}

	@Override
	public Patient getPatientAllergies(String email) {
		return patientRepository.getPatientWithAllergies(email);
	}

	@Override
	public void removeAllergy(String patientEmail, Long medicamentId) {
		Patient p = patientRepository.getPatientWithAllergies(patientEmail);

		Medicament m = medicamentRepository.findById(medicamentId).orElse(null);


		p.setAllergies(p.getAllergies().stream().filter(medicament -> !medicament.getId().equals(medicamentId)).collect(Collectors.toSet()));
		patientRepository.save(p);
	}

	@Override
	public Patient getOneWithePrescriptions(String email) {

		return patientRepository.getPatientWithePrescriptions(email);
	}

	@Override
	public Patient getOneOnlyePrescription(String email) {
		return patientRepository.getOneOnlyePrescription(email);
	}

	@Override
	public Patient getOneWithCategory(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkIfTermFilled(Patient patient, Appointment appointment) {
		boolean isFilled = false;
		for(Appointment a : patient.getAppointments()) {
			if(a.getDate().isEqual(appointment.getDate())) {
				if(a.getTermFrom().equals(appointment.getTermFrom()))
					isFilled = true;
				if(a.getTermTo().equals(appointment.getTermTo()))
					isFilled = true;
				
				if(a.getTermFrom().isAfter(appointment.getTermFrom()) && a.getTermFrom().isBefore(appointment.getTermTo()))
					isFilled = true;
				
				if(a.getTermTo().isAfter(appointment.getTermFrom()) && a.getTermTo().isBefore(appointment.getTermTo()))
					isFilled = true;
				if(a.getTermFrom().isBefore(appointment.getTermFrom()) && a.getTermTo().isAfter(appointment.getTermTo()))
					isFilled = true;
				
			}
		}
		return isFilled;
	}

	@Override
	public Patient getMedicamentIfPurchased(String email, Long id) {
		return patientRepository.getPatientPurchasedMedication(email, id);
	}

	@Override
	public Patient getPatientExaminationIfDone(String email, String ratedEmployeeEmail) {
		return patientRepository.getPatientDoneExamination(email, ratedEmployeeEmail);
	}

	@Override
	public Patient getPatientExaminationMedicationDone(String email) {
		return patientRepository.getPatientExaminationMedicationDone(email);
	}

}
