package com.mrsisa.mrsisaprojekat.S4.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.dto.ComplaintDTO;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Complaint;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.repository.ComplaintRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.SystemAdminDB;
import com.mrsisa.mrsisaprojekat.service.ComplaintServiceImpl;
import com.mrsisa.mrsisaprojekat.service.EmailService;
import com.mrsisa.mrsisaprojekat.service.PatientServiceImpl;
import com.mrsisa.mrsisaprojekat.service.SystemAdminServiceImpl;


@SpringBootTest
public class WriteResponseUnitTest {

	@Mock
	private SystemAdminServiceImpl adminServiceMock;
	
	@Mock 
	private EmailService emailServiceMock;
	@Mock
	private PatientServiceImpl patientServiceMock;
	
	@Mock
	private ComplaintRepositoryDB complaintRepositoryMock;
	
	@Mock
	private SystemAdminDB adminRepositoryMock;
	
	@InjectMocks
	private ComplaintServiceImpl complaintService;
	
	private AdminSystem adminMock;
	
	private Complaint complaintMock;
	
	private ComplaintDTO complaintDtoMock;
	
	private Patient patientMock;
	
	private Pharmacy pharmacyMock;
	
	@BeforeEach
	public void Setup() {
		
		patientMock = new Patient();
		adminMock = new AdminSystem();
		complaintMock = new Complaint();
		complaintDtoMock = new ComplaintDTO();
		pharmacyMock = new Pharmacy();
		
		complaintMock.setId(1L);
		patientMock.setEmail("pacijent@gmail.com");
		adminMock.setEmail("admin@gmail.com");
		
		pharmacyMock.setId(1L);
		
		complaintDtoMock.setId(1L);
		complaintDtoMock.setResponder("admin@gmail.com");
		complaintDtoMock.setPatient("pacijent@gmail.com");
		complaintDtoMock.setResponse("Neki odgvor");		
		complaintDtoMock.setPharmacy(1L);
	}
	
	@Test
	@Transactional
	@Rollback
	public void testValidComplaintResponse() {
		Mockito.doReturn(adminMock).when(adminServiceMock).findOneWithCompalints("admin@gmail.com");
		Mockito.doReturn(patientMock).when(patientServiceMock).findOne("pacijent@gmail.com");
		Mockito.doReturn(complaintMock).when(complaintRepositoryMock).findOneWithPharmacy(1L);
		Mockito.doReturn(complaintMock).when(complaintRepositoryMock).save(complaintMock);
		Mockito.doReturn(null).when(complaintRepositoryMock).findByResponder("admin@gmail.com");
		
		Mockito.doReturn(adminMock).when(adminRepositoryMock).save(adminMock);
		try {
			Mockito.doNothing().when(emailServiceMock).sendComplaintAnswer(complaintMock);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ComplaintDTO saved = complaintService.writeResponse(complaintDtoMock);
		assertEquals(saved.getResponse(), complaintDtoMock.getResponse());
		assertEquals(saved.getId(), complaintDtoMock.getId());
	}
	
	
	
	
	
}
