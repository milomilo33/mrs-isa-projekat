package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Async
	public void sendTestMail(User user) throws MailException, InterruptedException{
		System.out.println("Slanje emaila...");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Welcome");
		mail.setText("Welcome " +user.getEmail()+"!You have been registered. Your password is your phoneNumber and first cappital letter of you name and your lastname (NNNNNNNNNFL)");
		javaMailSender.send(mail);
		
		System.out.println("Email poslat!");
	}
	
	@Async
	public void ActivationMail(Patient patient) throws MailException, InterruptedException{
		
		System.out.println("Slanje emaila...");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(patient.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Aktivacioni link");
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendMedicineTakenConfirmationMail(ePrescription ePrescription) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		//mail.setTo(ePrescription.getPatient().getEmail());
		mail.setTo("filipovic.dada@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Potvrda preuzimanja leka");
		String text = "Preuzeti lekovi:\n";
		for (PrescriptionMedicament pm : ePrescription.getPrescriptionMedicaments()) {
			int quantity = pm.getQuantity();
			String med = pm.getMedicament().getName();
			text += med + " x" + quantity;
			text += "\n";
		}
		mail.setText(text);
		System.out.println(text);
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
    
	@Async
	public void ReservationConfirmationMail(Patient patient, PrescriptionMedicament medicament) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("nikolinatosic999@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Potvrda rezervacije leka");
		mail.setText("Uspešno ste izvršili rezervaciju leka. Kod rezervacije: " + medicament.getId() + "\n" +
				"Detalji rezervacije:\n" +
				"Količina rezervisanog leka: " + medicament.getQuantity() + "\n" +
				"Datum isteka rezervacije: " + medicament.getExpiryDate());
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}

	@Async
	public void ReserveExaminationMail(Patient patient, Long id) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(patient.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Potvrda rezervacije pregleda kod dermatologa");
		mail.setText("Uspesno ste izvrsili rezervaciju pregleda kod dermatologa. Kod rezervacije pregleda: " + id);
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendComplaintAnswer(Complaint complaint) throws MailException, InterruptedException{
		System.out.println("Slanje emaila... "+complaint.getPatient().getEmail());

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("filipovic.dada@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Complaint answer");
		mail.setText("'"+ complaint.getDescription()+ "'\n--> "+complaint.getResponse());
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
	
	@Async
	public void activationTokenMail(ConfirmationToken token, String email) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Account activation");
		mail.setText("To confirm your account, please click here : "
	            +"http://localhost:8080/api/patients/confirm-account?token="+token.getConfirmationToken()
				+"\n or with this link \n " +"https://mrs-isa-projekat-frontend.herokuapp.com/api/patients/confirm-account?token="+token.getConfirmationToken());
		
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
	
	
	@Async
	public void sendSupplierMail(Supplier supplier, Offer offer) throws MailException, InterruptedException {

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(supplier.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Information about your offer");
		mail.setText("Your offer with id: " + offer.getId()+" with deadline: "+offer.getDeadline()+" is "+ offer.getStatus()+"!");
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
	
	@Async
	public void appointmentScheduledMail(LocalDate date, LocalTime timeFrom, LocalTime timeTo, Employee employee, String patientEmail, String employeeType) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(patientEmail);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Appointment created and scheduled");
		mail.setText("Appointment scheduled.\n" + employeeType + ": " + employee.getName() + " " +
					employee.getLastName() + "\nDate: " + date + "\nFrom: " + timeFrom + "\nTo: " + timeTo);
		javaMailSender.send(mail);
	}
	
	
	@Async
	public void sendEmployeeMail(Employee employee, Request request) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("nikolinatosic999@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Information about your request");
		if(request.isAccepted()) {
			mail.setText("Your request for days off that start from " + request.getDateFrom()+" to "+ request.getDateTo()+ " is accepted!");
		}else {
			mail.setText("Your request for days off that start from " + request.getDateFrom()+" to "+ request.getDateTo()+ " is rejected! Rejection reason: "+ request.getRejectionReason());
		}
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
	
}
