package com.mrsisa.mrsisaprojekat.S3.unit;

import com.mrsisa.mrsisaprojekat.model.*;
import com.mrsisa.mrsisaprojekat.repository.AppointmentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.MedicalReportRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.ePrescriptionRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StartAppointmentUnitTest {

    @Mock
    private AppointmentRepositoryDB appointmentRepositoryMock;

    @Mock
    private ePrescriptionRepositoryDB ePrescriptionRepositoryMock;

    @Mock
    private MedicalReportRepositoryDB medicalReportRepositoryMock;

    @InjectMocks
    private AppointmentServiceImpl appointmentServiceMock;

    private Appointment appointmentMock;
    private Pharmacist pharmacistMock;
    private Pharmacy pharmacyMock;
    //private MedicalReport newReportMock;
    private Patient patientMock;

    private MedicalReport existingReportMock;
    private Appointment appointmentMock2;
    private Pharmacist pharmacistMock2;

    @BeforeEach
    public void Setup() {
        appointmentMock = new Appointment();
        pharmacistMock = new Pharmacist();
        pharmacyMock = new Pharmacy();
        //newReportMock = new MedicalReport();
        patientMock = new Patient();

        //newReportMock.setId(2L);

        pharmacyMock.setId(3L);
        pharmacyMock.setName("Neka apoteka");
        Set<Appointment> pharmacyAppointments = new HashSet<>();
        pharmacyAppointments.add(appointmentMock);
        pharmacyMock.setAppointments(pharmacyAppointments);

        pharmacistMock.setEmail("pharmacist@mail.com");
        pharmacistMock.setCurrentlyInAppointment(false);
        pharmacistMock.setPharmacy(pharmacyMock);

        patientMock.setEmail("patient@mail.com");
        patientMock.setePrescriptions(new HashSet<>());

        appointmentMock.setId(1L);
        appointmentMock.setDeleted(false);
        appointmentMock.setDone(false);
        appointmentMock.setChosenEmployee(pharmacistMock);
        appointmentMock.setMedicalReport(null);
        appointmentMock.setPatient(patientMock);
        appointmentMock.setDate(LocalDate.now());


        // drugi

        existingReportMock = new MedicalReport();
        appointmentMock2 = new Appointment();
        pharmacistMock2 = new Pharmacist();

        existingReportMock.setId(5L);

        appointmentMock2.setId(6L);
        appointmentMock2.setDeleted(false);
        appointmentMock2.setDone(false);
        appointmentMock2.setChosenEmployee(pharmacistMock2);
        appointmentMock2.setMedicalReport(existingReportMock);
        appointmentMock2.setPatient(patientMock);
        appointmentMock2.setDate(LocalDate.now());

        pharmacistMock2.setEmail("pharmacist2@mail.com");
        pharmacistMock2.setCurrentlyInAppointment(true);
        pharmacistMock2.setPharmacy(pharmacyMock);

        pharmacyMock.getAppointments().add(appointmentMock2);

        // treci
    }

    public MedicalReport simulateSaveReport(MedicalReport report) {
        report.setId(4L);
        return report;
    }

    @Test
    @Transactional
    @Rollback
    public void testValidUninitiatedAppointment() {
        Mockito.doReturn(Optional.of(appointmentMock)).when(appointmentRepositoryMock).findById(1L);
        Mockito.when(ePrescriptionRepositoryMock.save(Mockito.any(ePrescription.class))).thenReturn(null);
        Mockito.when(medicalReportRepositoryMock.save(Mockito.any(MedicalReport.class))).thenAnswer(i -> simulateSaveReport((MedicalReport) i.getArguments()[0]));

        Long medicalReportId = appointmentServiceMock.startAppointment(1L, "pharmacist@mail.com");
        assertEquals(4L, medicalReportId);
        assertTrue(pharmacistMock.isCurrentlyInAppointment());
        assertEquals(appointmentMock.getMedicalReport().getId(), medicalReportId);
    }

    @Test
    @Transactional
    @Rollback
    public void testValidInitiatedAppointment() {
        Mockito.doReturn(Optional.of(appointmentMock2)).when(appointmentRepositoryMock).findById(6L);

        Long medicalReportId = appointmentServiceMock.startAppointment(6L, "pharmacist2@mail.com");
        assertEquals(5L, medicalReportId);
    }

    @Test
    @Transactional
    @Rollback
    public void testInvalidDateStartAppointment() {
        LocalDate backup = appointmentMock2.getDate();
        appointmentMock2.setDate(LocalDate.now().minusDays(1));
        Mockito.doReturn(Optional.of(appointmentMock2)).when(appointmentRepositoryMock).findById(6L);

        Long medicalReportId = appointmentServiceMock.startAppointment(6L, "pharmacist2@mail.com");
        assertNull(medicalReportId);
        appointmentMock2.setDate(backup);
    }
}
