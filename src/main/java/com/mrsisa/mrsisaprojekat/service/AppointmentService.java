package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.Appointment;

import java.util.Collection;

public interface AppointmentService {
    Collection<Appointment> findAll();

    Appointment findOne(Long id);

    Appointment create(Appointment appointment) throws Exception;

    Appointment update(Appointment appointment) throws Exception;

    void delete(Appointment appointment);

    boolean markPatientAbsence(Long appointmentId);
}
