package com.mrsisa.mrsisaprojekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.model.ConfirmationToken;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.User;
import com.mrsisa.mrsisaprojekat.model.ePrescription;

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
		mail.setText("You have been registered. Welcome! Your password is your phoneNumber and first cappital letter of you name and your lastname (NNNNNNNNNFL)");
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
		mail.setTo(ePrescription.getPatient().getEmail());
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
	public void ReservationConfirmationMail(Patient patient, Long id) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(patient.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Potvrda rezervacije leka");
		mail.setText("Uspesno ste izvrsili rezervaciju leka. Kod rezervacije: " + id);
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
	public void activationTokenMail(ConfirmationToken token, String email) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Aktivacija naloga");
		mail.setText("To confirm your account, please click here : "
	            +"http://localhost:8080/api/patients/confirm-account?token="+token.getConfirmationToken());
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
	
	
}
