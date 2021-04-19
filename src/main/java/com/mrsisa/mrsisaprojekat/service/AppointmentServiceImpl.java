package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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
        return null;
    }

    @Override
    public Appointment update(Appointment appointment) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

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
}
