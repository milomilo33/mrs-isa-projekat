package com.mrsisa.mrsisaprojekat.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.ePrescription;
import com.mrsisa.mrsisaprojekat.repository.ePrescriptionRepositoryDB;

@Service
public class ePrescriptionServiceImpl implements ePrescriptionService {

	@Autowired
	private ePrescriptionRepositoryDB ePrescriptionRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public List<ePrescription> findAll() {
		return ePrescriptionRepository.findAll();
	}

	@Override
	public ePrescription findOne(Long id) {
		return ePrescriptionRepository.findById(id).orElse(null);
	}

	@Override
	public ePrescription create(ePrescription ePrescription) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ePrescription update(ePrescription ePrescription) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		ePrescription ePrescription = this.findOne(id);
		if (ePrescription == null) {
			return false;
		}
		
		ePrescriptionRepository.deleteOne(id);
		return true;
	}

	@Override
	@Transactional
	public boolean dispensePrescription(Long id, Pharmacist pharmacist) {
		ePrescription ePrescription = this.findOne(id);
		if (ePrescription == null) {
			return false;
		}

		boolean pharmacistInPrescriptionPharmacy = false;
		for (Pharmacist ph : ePrescription.getPharmacy().getPharmacists()) {
			if (ph.getEmail().equals(pharmacist.getEmail())) {
				pharmacistInPrescriptionPharmacy = true;
				break;
			}
		}
		if (!pharmacistInPrescriptionPharmacy) {
			return false;
		}
		
		LocalDate date = ePrescription.getDate();
		LocalDate now = LocalDate.now();
		
		Period period = Period.between(now, date);
		if (period.getDays() < 1) {
			return false;
		}
		
		if (ePrescription.isDone() || ePrescription.isDeleted()) {
			return false;
		}
		
		ePrescriptionRepository.done(id);
		Hibernate.initialize(ePrescription.getPatient());
		Hibernate.initialize(ePrescription.getPrescriptionMedicaments());
		Hibernate.initialize(ePrescription.getPrescriptionMedicaments());
		for (PrescriptionMedicament pm : ePrescription.getPrescriptionMedicaments()) {
			Hibernate.initialize(pm.getMedicament());
		}
		try {
			emailService.sendMedicineTakenConfirmationMail(ePrescription);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	@Transactional
	public ePrescription findPrescriptionForPharmacist(Long id, Pharmacist pharmacist) {
		ePrescription ePrescription = this.findOne(id);
		if (ePrescription == null) {
			return null;
		}

		boolean pharmacistInPrescriptionPharmacy = false;
		for (Pharmacist ph : ePrescription.getPharmacy().getPharmacists()) {
			if (ph.getEmail().equals(pharmacist.getEmail())) {
				pharmacistInPrescriptionPharmacy = true;
				break;
			}
		}
		if (!pharmacistInPrescriptionPharmacy) {
			return null;
		}

		LocalDate date = ePrescription.getDate();
		LocalDate now = LocalDate.now();
		
		Period period = Period.between(now, date);
		if (period.getDays() < 1) {
			return null;
		}
		
		if (ePrescription.isDone() || ePrescription.isDeleted()) {
			return null;
		}
		
		Hibernate.initialize(ePrescription.getPatient());
		Hibernate.initialize(ePrescription.getPrescriptionMedicaments());
		Hibernate.initialize(ePrescription.getPrescriptionMedicaments());
		for (PrescriptionMedicament pm : ePrescription.getPrescriptionMedicaments()) {
			Hibernate.initialize(pm.getMedicament());
		}
		Hibernate.unproxy(ePrescription);
		return ePrescription;
	}

}
