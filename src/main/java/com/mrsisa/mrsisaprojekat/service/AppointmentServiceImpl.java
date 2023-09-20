package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.AppointmentDetailsDTO;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.model.Appointment.AppointmentType;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.DermatologistRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.MedicalReportRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PatientRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.ePrescriptionRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepositoryDB appointmentRepository;

    @Autowired
    private ePrescriptionRepositoryDB ePrescriptionRepository;

    @Autowired
    private MedicalReportRepositoryDB medicalReportRepository;
    
    
    @Autowired
    private PatientRepositoryDB patientRepository;
    
    @Autowired
    private DermatologistRepositoryDB dermatologistRepository;
    
    @Autowired
    private PharmacyRepositoryDB pharmacyRepository;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private EmailService mailService;



    @Override
    public Collection<Appointment> findAll() {
        return null;
    }

    @Override
    public Appointment findOne(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public Appointment create(Appointment appointment) throws Exception {
       return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) throws Exception {

        return null;
    }

    @Override
    public void delete(Appointment a) {
        a.setDeleted(true);
        appointmentRepository.save(a);
    }

    @Override
    @Transactional
    public boolean markPatientAbsence(Long appointmentId) {
        Appointment appointment = this.findOne(appointmentId);

        if (appointment == null) {
            return false;
        }

        if (appointment.isDeleted() || appointment.isDone()) {
            return false;
        }

        // zavrsi pregled
        appointment.setDone(true);
        appointment.setMedicalReport(null);

        // dodaj 1 penalty poen pacijentu
        appointment.getPatient().setPenaltyPoints(appointment.getPatient().getPenaltyPoints() + 1);

        appointment.getChosenEmployee().setCurrentlyInAppointment(false);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentDetailsDTO getAppointmentDetails(Long appointmentId) {
        Appointment appointment = this.findOne(appointmentId);

        if (appointment == null) {
            return null;
        }

        if (appointment.isDeleted()) {
            return null;
        }

        Map<String, Integer> medicineQuantity = new HashMap<String, Integer>();
        String reportText = appointment.getMedicalReport().getDescription();

        Set<PrescriptionMedicament> prescriptionMedicaments = appointment.getMedicalReport().getEprescription().getPrescriptionMedicaments();
        for (PrescriptionMedicament pm : prescriptionMedicaments) {
            String medicineName = pm.getMedicament().getName();
            Integer quantity = pm.getQuantity();
            if (!medicineQuantity.containsKey(medicineName)) {
                medicineQuantity.put(medicineName, quantity);
            }
            else {
                medicineQuantity.put(medicineName, medicineQuantity.get(medicineName) + quantity);
            }
        }

        AppointmentDetailsDTO details = new AppointmentDetailsDTO(reportText, medicineQuantity);

        return details;
    }

    @Override
    public boolean cancelExamination(Long id) {
        Appointment a = appointmentRepository.findById(id).orElse(null);
        LocalDateTime now = LocalDateTime.now().plusDays(1);

        if(a != null) {
            if(now.isBefore(a.getDate().atTime(a.getTermFrom()))) {
                a.setPatient(null);
                appointmentRepository.save(a);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Long startAppointment(Long appointmentId, String employeeEmail) {
        Appointment appointment = this.findOne(appointmentId);

        if (appointment == null) {
            return null;
        }

        if (appointment.isDeleted() || appointment.isDone()) {
            return null;
        }

        if (!employeeEmail.equals(appointment.getChosenEmployee().getEmail())) {
            return null;
        }

        LocalDate appointmentDate = appointment.getDate();
        LocalDate today = LocalDate.now();
        if (today.isAfter(appointmentDate)) {
            return null;
        }

        MedicalReport report = appointment.getMedicalReport();
        if (report != null) {
            return report.getId();
        }
        else {
            if (appointment.getChosenEmployee().isCurrentlyInAppointment()) {
                return null;
            }
            appointment.getChosenEmployee().setCurrentlyInAppointment(true);

            Patient patient = appointment.getPatient();
            LocalDate date = LocalDate.now().plusDays(7);
            Set<PrescriptionMedicament> prescriptionMedicaments = new HashSet<>();
            Pharmacy pharmacy = null;
            if (appointment.getChosenEmployee() instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) appointment.getChosenEmployee();
                pharmacy = pharmacist.getPharmacy();
            }
            else {
                // dermatologist
                Dermatologist dermatologist = (Dermatologist) appointment.getChosenEmployee();
                Set<Pharmacy> pharmacies = dermatologist.getPharmacies();
                for (Pharmacy p : pharmacies) {
                    for (Appointment a : p.getAppointments()) {
                        if (a.getId().equals(appointmentId)) {
                            pharmacy = p;
                            break;
                        }
                    }
                }
            }
            // dodati price
            ePrescription ePrescription = new ePrescription(patient, date, prescriptionMedicaments, pharmacy, null,0);
            ePrescriptionRepository.save(ePrescription);
            patient.getePrescriptions().add(ePrescription);

            MedicalReport newReport = new MedicalReport("", LocalDateTime.now(), ePrescription);
            medicalReportRepository.save(newReport);

            appointment.setMedicalReport(newReport);

            return newReport.getId();
        }
    }

    @Override
    @Transactional
    public Boolean finishAppointment(Long appointmentId, String employeeEmail, String reportText) {
        Appointment appointment = this.findOne(appointmentId);

        if (appointment == null) {
            return null;
        }

        if (appointment.isDeleted() || appointment.isDone()) {
            return null;
        }

        if (!employeeEmail.equals(appointment.getChosenEmployee().getEmail())) {
            return false;
        }

        MedicalReport report = appointment.getMedicalReport();
        report.setDescription(reportText);

        appointment.setDone(true);

        appointment.getChosenEmployee().setCurrentlyInAppointment(false);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Pharmacy getPharmacyOfAppointment(Long appointmentId) {
        Appointment appointment = this.findOne(appointmentId);

        if (appointment == null) {
            return null;
        }

        if (appointment.isDeleted()) {
            return null;
        }

        if (appointment.getMedicalReport() == null) {
            return null;
        }

        Pharmacy pharmacy = appointment.getMedicalReport().getEprescription().getPharmacy();
        pharmacy.setMedicamentItems(null);
        pharmacy.setAdmins(null);
        pharmacy.setAppointments(null);
        pharmacy.setDermatologists(null);
        pharmacy.setOrders(null);
        pharmacy.setPharmacists(null);
        pharmacy.setPricelistAppointments(null);
        pharmacy.setPricelistMedicaments(null);
        pharmacy.setRatings(null);
        pharmacy.setRequests(null);
        pharmacy.setVacations(null);

        return pharmacy;
    }

	@Override
	public Set<Appointment> getAppointmentWithEmployee(String email) {
		Set<Appointment> appointments = appointmentRepository.getOneAppointmentWithEmployee(email);
		if(appointments == null) {
			return null;
		}
		return appointments;
	}

	@Override
	@Transactional
	public Appointment reserveAppointment(AppointmentDTO appointment) {
		Appointment appointmentToReserve = appointmentRepository.findOneAppointment(appointment.getAppointmentId());
		Patient p = patientRepository.findById(appointment.getPatientEmail()).orElse(null);
		if(p == null) {
			return null;
		}
		Patient patient = patientService.getOneWithAppointments(appointment.getPatientEmail());
		boolean isReserved = patientService.checkIfTermFilled(patient, appointmentToReserve);
		appointmentToReserve.setPatient(p);
		
		if(!isReserved) {
			//System.out.println("DAAAAAAAAAAAAAAAAA"+patient.getEmail());
			//appointmentService.update(appointmentToReserve);
			Long id = patientService.updateWithAppointment(patient, appointmentToReserve);
			try {
				mailService.ReserveExaminationMail(patient, id);
			}
			catch( Exception e) {
				System.out.println(e.getMessage());
			}
			return appointmentToReserve;
		} else {
			return null;
		}
	}

	@Override
	public Appointment makeAppointmentDermatologist(Appointment appointment,Long id, String email) {
		Appointment a = new Appointment();
		a.setMedicalReport(null);
		a.setDeleted(false);
		a.setPatient(null);
		Dermatologist d = dermatologistRepository.getOneDermatologist(email);
		if(d!= null) {
			if(check(d.getWorkHour(), appointment.getDate(), appointment.getTermFrom(),appointment.getTermTo())) {
				//return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				return null;
			}
			if(checkAppointment(d.getMedicalExaminations(), appointment.getDate(), appointment.getTermFrom(), appointment.getTermTo())) {
				//return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				return null;
			}
			d.setRatings(null);
			d.setRequests(null);
			a.setChosenEmployee(d);
			a.setDate(appointment.getDate());
			a.setTermFrom(appointment.getTermFrom());
			a.setTermTo(appointment.getTermTo());
			a.setType(AppointmentType.EXAMINATION);
		}
		
	
		Appointment savedAppointment = appointmentRepository.save(a);
		Pharmacy p = pharmacyRepository.getOneWithAppointments(id);
		if (p == null) {
			return null;
		}
		p.getAppointments().add(savedAppointment);
		pharmacyRepository.save(p);
		
		return savedAppointment;
	}
	
	
	@Override
	 public boolean check(Set<WorkHour> workHours, LocalDate date, LocalTime time1, LocalTime time2) {
	    	for(WorkHour w : workHours) {
	    		if(w.getDay().ordinal()+1 == date.getDayOfWeek().getValue() && time1.compareTo(w.getWorkHourFrom())<0 &&
	    				w.getWorkHourTo().compareTo(time2) >0 || time2.compareTo(w.getWorkHourFrom()) <0 ||
	    				w.getDay().ordinal()+1 == date.getDayOfWeek().getValue() &&  w.getWorkHourFrom().compareTo(time1)>0 && w.getWorkHourTo().compareTo(time2)>0 && time2.compareTo(w.getWorkHourFrom())>0 ||	
	    				w.getDay().ordinal()+1 == date.getDayOfWeek().getValue()  && time1.compareTo(w.getWorkHourFrom())>0 && time2.compareTo(w.getWorkHourTo())>0 && time1.compareTo(w.getWorkHourTo())<0 || 
	    				w.getDay().ordinal()+1 != date.getDayOfWeek().getValue() ) {
					
	    			return true;
				}
	    	}
				
							
	    	return  false;
	    }
	@Override
	    public boolean checkAppointment(Set<Appointment> appointments, LocalDate date, LocalTime time1, LocalTime time2) {
	    	for(Appointment a : appointments) {
	    		if(!a.isDeleted()) {
	    		if(a.getDate().equals(date) && time1.compareTo(a.getTermFrom()) == 0
	    				&& time2.compareTo(a.getTermTo()) == 0 ||a.getDate().equals(date) && a.getTermFrom().compareTo(time1)<=0 && a.getTermTo().compareTo(time2) >=0 && time2.compareTo(a.getTermFrom())<=0
	    				|| a.getDate().equals(date) && a.getTermFrom().compareTo(time1)>=0 && a.getTermTo().compareTo(time2)>=0 && time2.compareTo(a.getTermFrom()) >=0 ||
	    				a.getDate().equals(date)  && time1.compareTo(a.getTermFrom())>=0 &&  time2.compareTo(a.getTermTo())>=0 && time1.compareTo(a.getTermTo())<=0) {
					return true;
				}
	    	}
	    	
	    	}
	    	return  false;
	    }
	
}
