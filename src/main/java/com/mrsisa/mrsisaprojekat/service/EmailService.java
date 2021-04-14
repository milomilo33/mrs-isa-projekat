package com.mrsisa.mrsisaprojekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
	public void ReservationConfirmationMail(Patient patient) throws MailException, InterruptedException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("marko.suljak80@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Potvrda rezervacije leka");
		mail.setText("Uspesno ste izvrsili rezervaciju leka. Kod rezervacije: ");
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
}
