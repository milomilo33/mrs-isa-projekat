package com.mrsisa.mrsisaprojekat.S1.unit;

import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.MedicamentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PatientRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.AppointmentServiceImpl;
import com.mrsisa.mrsisaprojekat.service.PatientService;
import com.mrsisa.mrsisaprojekat.service.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AddAllergyUnitTest {
    @Mock
    private PatientRepositoryDB patientRepositoryMock;

    @Mock
    private MedicamentRepositoryDB medicamentRepositoryMock;

    @Mock
    private AppointmentRepositoryDB appointmentRepositoryMock;

    @InjectMocks
    private PatientServiceImpl patientServiceMock;

    @InjectMocks
    private AppointmentServiceImpl appointmentServiceMock;

    private Patient patientMock;
    private Medicament medicamentMock;
    private Appointment appointmentMock;


    @BeforeEach
    public void Setup() {
        patientMock = new Patient();
        medicamentMock = new Medicament();
        appointmentMock = new Appointment();

        medicamentMock.setRatings(new HashSet<>());

        patientMock.setEmail("mock@mail.com");
        patientMock.setAllergies(new HashSet<>());

        appointmentMock.setId(1L);
        appointmentMock.setPatient(patientMock);
        appointmentMock.setTermFrom(LocalTime.now());
        appointmentMock.setDate(LocalDate.now().plusDays(2));
        medicamentMock.setId(1L);

        patientRepositoryMock.save(patientMock);
    }

    @Test
    //@Transactional
    @Rollback
    public void testAddAllergy() throws Exception {
        Mockito.doReturn(patientMock).when(patientRepositoryMock).getPatientWithAllergies("mock@mail.com");
        Mockito.doReturn(Optional.of(medicamentMock)).when(medicamentRepositoryMock).findById(1L);
        Mockito.when(medicamentRepositoryMock.save(Mockito.any(Medicament.class))).thenReturn(null);
        Mockito.when(patientRepositoryMock.save(Mockito.any(Patient.class))).thenReturn(null);

        patientServiceMock.addAllergy("mock@mail.com", 1L);
        assertTrue(patientMock.getAllergies().stream().anyMatch(p -> p.getId() == 1L));

    }

    @Test
    @Rollback
    public void testRemoveAllergy() throws Exception {
        patientMock.getAllergies().add(medicamentMock);

        Mockito.doReturn(patientMock).when(patientRepositoryMock).getPatientWithAllergies("mock@mail.com");
        Mockito.doReturn(Optional.of(medicamentMock)).when(medicamentRepositoryMock).findById(1L);
        Mockito.when(medicamentRepositoryMock.save(Mockito.any(Medicament.class))).thenReturn(null);
        Mockito.when(patientRepositoryMock.save(Mockito.any(Patient.class))).thenReturn(null);

        patientServiceMock.removeAllergy("mock@mail.com", 1L);
        assertFalse(patientMock.getAllergies().stream().anyMatch(p -> p.getId() == 1L));
    }

    @Test
    @Rollback
    public void cancelExaminationTest() {
        Mockito.doReturn(Optional.of(appointmentMock)).when(appointmentRepositoryMock).findById(1L);

        boolean isCanceled = appointmentServiceMock.cancelExamination(1L);
        assertTrue(isCanceled);

    }



}
