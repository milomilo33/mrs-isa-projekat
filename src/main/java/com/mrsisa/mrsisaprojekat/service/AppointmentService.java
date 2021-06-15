package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.AppointmentDetailsDTO;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;

import java.util.Collection;
import java.util.Set;

public interface AppointmentService {
    Collection<Appointment> findAll();

    Appointment findOne(Long id);

    Appointment create(Appointment appointment) throws Exception;

    Appointment update(Appointment appointment) throws Exception;

    void delete(Appointment appointment);

    boolean markPatientAbsence(Long appointmentId);

    AppointmentDetailsDTO getAppointmentDetails(Long appointmentId);

    boolean cancelExamination(Long id);

    Long startAppointment(Long appointmentId, String employeeEmail);

    Boolean finishAppointment(Long appointmentId, String employeeEmail, String reportText);

    Pharmacy getPharmacyOfAppointment(Long appointmentId);
    
    Set<Appointment> getAppointmentWithEmployee(String email);
    
    Appointment reserveAppointment(AppointmentDTO appointment);
}
