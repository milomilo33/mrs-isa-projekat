package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.dto.ComplaintDTO;
import com.mrsisa.mrsisaprojekat.model.AdminSystem;
import com.mrsisa.mrsisaprojekat.model.Complaint;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.repository.ComplaintRepositoryDB;

@Service
public class ComplaintServiceImpl implements ComplaintService{

	
	@Autowired
	private ComplaintRepositoryDB complaintRepository;
	
	@Autowired 
	private EmailService emailService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private SystemAdminService adminService;
	
	@Override
	public Collection<Complaint> findAll() {
		return complaintRepository.findAll();
	}

	@Override
	public Collection<Complaint> findAllUnanswered() {
		return complaintRepository.findByResponseIsNull();
	}

	@Override
	public Collection<Complaint> findAllAnswered() {
		return complaintRepository.findByResponseIsNotNull();
	}

	@Override
	public Complaint create(Complaint complaint) {
		return complaintRepository.save(complaint);
	}

	@Override
	public Complaint update(Complaint complaint) {
		return complaintRepository.save(complaint);
	}

	@Override
	@Transactional
	public ComplaintDTO writeResponse(ComplaintDTO complaintDTO) {
		AdminSystem admin = adminService.findOneWithCompalints(complaintDTO.getResponder());
		if (admin == null ) {
			return null;
		}
		
		Patient patient = patientService.findOne(complaintDTO.getPatient());
		if (patient == null ) {
			return null;
		}
		
		Complaint complaint = null;
		if(complaintDTO.getEmployee()!= null) {
			complaint = this.findOneWithEmployee(complaintDTO.getId());
		}
		else {
			complaint = this.findOneWithPharmacy(complaintDTO.getId());
		}
		
		if(complaint == null) {
			return null;
		}
		
		try{
			if(complaint.getResponse() != null || complaint.getResponse().equalsIgnoreCase("")) {
				return null;
			}
		}
		catch(NullPointerException e) {
			
		}
		complaint.setResponder(admin);
		complaint.setPatient(patient);
		complaint.setResponse(complaintDTO.getResponse());
		
		Complaint saved = this.update(complaint);
		
		Set<Complaint> responseComplaints = this.getResponderComplaint(admin.getEmail());
		if(responseComplaints == null) {
			responseComplaints = new HashSet<Complaint>();
		}
		responseComplaints.add(saved);
		try {
			adminService.update(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			emailService.sendComplaintAnswer(complaint);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new ComplaintDTO(saved.getId(), saved.getDescription(), saved.getResponder().getEmail(), saved.getResponse());
	}
	@Override
	public void delete(Long id) {
		complaintRepository.deleteById(id);
		
	}

	@Override
	public Set<Complaint> getResponderComplaint(String email) {
		return complaintRepository.findByResponder(email);
	}

	@Override
	public Complaint findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Complaint findOneWithEmployee(Long id) {
		return complaintRepository.findOneWithEmployee(id);
	}

	@Override
	public Complaint findOneWithPharmacy(Long id) {
		// TODO Auto-generated method stub
		return complaintRepository.findOneWithPharmacy(id);
	}

	@Override
	public Collection<Complaint> findPatientnsUnasnswered(String email) {
		// TODO Auto-generated method stub
		return complaintRepository.findByPatient(email);
	}



}
