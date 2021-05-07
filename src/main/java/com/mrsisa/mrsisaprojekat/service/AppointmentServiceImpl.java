package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDetailsDTO;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepositoryDB appointmentRepository;

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
            medicineQuantity.put(medicineName, quantity);
        }

        AppointmentDetailsDTO details = new AppointmentDetailsDTO(reportText, medicineQuantity);

        return details;
    }

    @Override
    public void cancelExamination(Appointment appointment) {
        appointment.setPatient(null);
        appointmentRepository.save(appointment);
    }
}
