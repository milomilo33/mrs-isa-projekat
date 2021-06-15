package com.mrsisa.mrsisaprojekat.S2.unit;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.WorkHour;
import com.mrsisa.mrsisaprojekat.model.WorkHour.Day;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.DermatologistRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.WorkHourRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.AppointmentServiceImpl;

@SpringBootTest
public class DermatologistAppointmentUnitTest {
	

    @Mock
    private AppointmentRepositoryDB appointmentRepositoryMock;
    
    @Mock
    private WorkHourRepositoryDB workHourRepositoryMock;

    @Mock
	private PharmacyRepositoryDB pharmacyRepositoryMock;
    
    @Mock
    private DermatologistRepositoryDB dermatologistRepositoryMock;

    @InjectMocks
    private AppointmentServiceImpl appointmentServiceMock;

    private Appointment appointmentMock;
    
    private Pharmacy pharmacyMock;
    
    private Pharmacy pharmacyMock2;
    
    private Dermatologist dermatologistMock;
    
    private Set<WorkHour> workHoursMock;

    @BeforeEach
    public void Setup() {
        appointmentMock = new Appointment();
        dermatologistMock = new Dermatologist();
      
        workHoursMock = new HashSet<>();
        pharmacyMock = new Pharmacy();
        pharmacyMock2 = new Pharmacy();
       
        pharmacyMock.setId(1L);
        pharmacyMock2.setId(2L);
        
       
        Set<Appointment> pharmacyAppointments = new HashSet<>();
        pharmacyAppointments.add(appointmentMock);
        pharmacyMock.setAppointments(pharmacyAppointments);
        
        
        WorkHour w1 = new WorkHour();
        w1.setDay(Day.MONDAY);
        w1.setDeleted(false);
        w1.setId(1L);
        w1.setWorkHourFrom(LocalTime.of(12, 00));
        w1.setWorkHourTo(LocalTime.of(14, 00));
        w1.setPharmacy(pharmacyMock);
        
        
        WorkHour w2 = new WorkHour();
        w2.setDay(Day.FRIDAY);
        w2.setDeleted(false);
        w2.setId(1L);
        w2.setWorkHourFrom(LocalTime.of(8,00));
        w2.setWorkHourTo(LocalTime.of(10,00));
        w2.setPharmacy(pharmacyMock2);
        
        workHoursMock.add(w1);
        workHoursMock.add(w2);
        
        
        dermatologistMock.setEmail("pera@gmail.com");
        dermatologistMock.setMedicalExaminations(pharmacyAppointments);
       
        
        Set<Pharmacy> pharmacies = new HashSet<>();
        pharmacies.add(pharmacyMock);
        pharmacies.add(pharmacyMock2);
        dermatologistMock.setPharmacies(pharmacies);
        dermatologistMock.setWorkHour(workHoursMock);
        

        appointmentMock.setId(1L);
        appointmentMock.setDeleted(false);
        appointmentMock.setDone(false);
        appointmentMock.setChosenEmployee(dermatologistMock);
        appointmentMock.setMedicalReport(null);
        appointmentMock.setPatient(null);
        appointmentMock.setDate(LocalDate.now());
    }

    @Test
    @Transactional
    @Rollback
    public void testInvalidWorkHourAppointment() {
    	
        Appointment a  = appointmentServiceMock.makeAppointmentDermatologist(appointmentMock,pharmacyMock.getId(), dermatologistMock.getEmail());
        assertNull(a);
       
    }

}
