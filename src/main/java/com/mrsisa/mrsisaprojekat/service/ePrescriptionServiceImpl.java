package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.PrescriptionMedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.MedicalReport;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.ePrescription;
import com.mrsisa.mrsisaprojekat.repository.ePrescriptionRepositoryDB;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ePrescriptionServiceImpl implements ePrescriptionService {

	@Autowired
	private ePrescriptionRepositoryDB ePrescriptionRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private MedicalReportService medicalReportService;
	
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
		return ePrescriptionRepository.save(ePrescription);
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
	public boolean dispensePrescription(Long id, String pharmacistEmail) throws ObjectOptimisticLockingFailureException {
		ePrescription ePrescription = this.findOne(id);
		if (ePrescription == null) {
			return false;
		}

		boolean pharmacistInPrescriptionPharmacy = false;
		for (Pharmacist ph : ePrescription.getPharmacy().getPharmacists()) {
			if (ph.getEmail().equals(pharmacistEmail)) {
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

	@Override
	@Transactional(readOnly = true)
	public Collection<PrescriptionMedicamentDTO> getPrescriptionMedicamentsForMedicalReport(Long medicalReportId) {
		MedicalReport report = medicalReportService.findOne(medicalReportId);

		if (report == null) {
			return null;
		}

		if (report.isDeleted()) {
			return null;
		}

		Collection<PrescriptionMedicament> prescriptionMedicaments = report.getEprescription().getPrescriptionMedicaments();

		if (prescriptionMedicaments == null) {
			return null;
		}

		Collection<PrescriptionMedicamentDTO> dtos = new HashSet<>();
		for (PrescriptionMedicament pm : prescriptionMedicaments) {
			PrescriptionMedicamentDTO dto = new PrescriptionMedicamentDTO();
			dto.setMedicament(pm.getMedicament());
			dto.getMedicament().setRatings(null);
			dto.getMedicament().setSubstituteMedicaments(null);
			dto.setQuantity(pm.getQuantity());
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public Set<ePrescription> findAllePrescriptionsInPharmacy(Long id) {
		Set<ePrescription> ePrescriptions = ePrescriptionRepository.findPharmacyePrescriptions(id);
		if(ePrescriptions == null) {
			return null;
		}
		return ePrescriptions;
	}

}
