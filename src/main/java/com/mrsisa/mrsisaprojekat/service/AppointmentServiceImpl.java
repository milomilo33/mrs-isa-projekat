package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDetailsDTO;
import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.MedicalReportRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.ePrescriptionRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepositoryDB appointmentRepository;

    @Autowired
    private ePrescriptionRepositoryDB ePrescriptionRepository;

    @Autowired
    private MedicalReportRepositoryDB medicalReportRepository;


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
    public void cancelExamination(Appointment appointment) {
        appointment.setPatient(null);
        appointmentRepository.save(appointment);
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
}
