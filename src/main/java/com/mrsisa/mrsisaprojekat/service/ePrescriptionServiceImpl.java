package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.PrescriptionMedicamentDTO;
import com.mrsisa.mrsisaprojekat.dto.QRCodePharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.QRItemDTO;
import com.mrsisa.mrsisaprojekat.model.CategoryThresholds;
import com.mrsisa.mrsisaprojekat.model.MedicalReport;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.ePrescription;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.ePrescriptionRepositoryDB;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ePrescriptionServiceImpl implements ePrescriptionService {

	@Autowired
	private ePrescriptionRepositoryDB ePrescriptionRepository;

	@Autowired
	private PharmacyRepositoryDB pharmacyRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private MedicalReportService medicalReportService;
	
	@Autowired
	private PharmacyService pharmacyService;
	 
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicamentService medicamentService;

	@Autowired
	private CategoryThresholdsService categoryService;
	
	
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
	
	@Override
	@Transactional
	public QRCodePharmacyDTO createePrescription(QRCodePharmacyDTO dto) {
		Patient p = patientService.getOneWithePrescriptions(dto.getQrCode().getPatient());
		if(p == null) {
			return null;
		}
		
		Pharmacy pharmacy = pharmacyService.findOneWithMedicaments(dto.getPharmacy().getId());
		if(pharmacy == null) {
			return null;
		}
		
		for(MedicamentItem m : pharmacy.getMedicamentItems()) {
			for(QRItemDTO item : dto.getQrCode().getPrescriptionMedicaments()) {
				if(m.getMedicament().getId() == item.getMedicamentId()) {
					m.setQuantity(m.getQuantity()-item.getQuantity());
				}
			}
		}
		
		
		int points = 0;
		Set<PrescriptionMedicament> prescribedMeds = new HashSet<PrescriptionMedicament>();
		for(QRItemDTO item : dto.getQrCode().getPrescriptionMedicaments()) {
			PrescriptionMedicament pm = new PrescriptionMedicament();
			pm.setPurchased(true);
			pm.setDeleted(false);
			pm.setExpiryDate(LocalDate.now());
			pm.setQuantity(item.getQuantity());
			pm.setMedicament(medicamentService.findOne(item.getMedicamentId()));
			prescribedMeds.add(pm);
			points += (item.getPoints()*item.getQuantity());
		}
		
		p.setLoyaltyPoints(p.getLoyaltyPoints()+points);
		
		CategoryThresholds ct = new CategoryThresholds();
		List<CategoryThresholds> cts = categoryService.findAll();
		int index = 0;
		for(CategoryThresholds c: cts) {
			if(p.getLoyaltyPoints() > c.getThreshold()) {
				index = index + 1;
			}	
		}
		if(index == cts.size()) {
			index = index - 1;
		}
		ct = cts.get(index);
		
		int discount = ct.getDiscount();
		
		double totalPrice = dto.getPharmacy().getCost() - ((dto.getPharmacy().getCost() * discount)/100);
		
		p.setCategory(ct.getCategory());
		
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(dto.getQrCode().getDate(), formatter);
		LocalDate now = LocalDate.now();
		
		Period period = Period.between(now, localDate);
		if (period.getDays() < 1) {
			return null;
		}
		
		ePrescription prescription = new ePrescription();
		prescription.setDate(localDate);
		prescription.setDeleted(false);
		prescription.setDone(true);
		prescription.setPatient(p);
		prescription.setPrescriptionMedicaments(prescribedMeds);
		prescription.setPrice(totalPrice);
		prescription.setTakenDate(LocalDate.now());
		prescription.setPharmacy(pharmacy);
		
		try {
			prescription = this.create(prescription);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(prescription == null) {
			return null;
		}
		
		try {
			pharmacy = pharmacyService.update(pharmacy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Set<ePrescription> prescriptions = p.getePrescriptions();
		prescriptions.add(prescription);
		
		p.setePrescriptions(prescriptions);
		
		p.setCategory(ct.getCategory());
		
		try {
			patientService.update(p);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		dto.setId(prescription.getId());
		
		try {
			emailService.sendMedicineTakenConfirmationMail(prescription);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public boolean createePrescriptionPatient(PrescriptionMedicamentDTO medicament) {
		Patient p = patientService.findOne(medicament.getPatientEmail());
		p.setePrescriptions(new HashSet<>());
		p.setePrescriptions(ePrescriptionRepository.findByPatient(medicament.getPatientEmail()));
		System.out.println("Velicina " + p.getePrescriptions().size());

		ePrescription e = new ePrescription();
		e.setPatient(p);
		e.setTakenDate(LocalDate.now());
		e.setDate(medicament.getExpiryDate());
		e.setPharmacy(pharmacyService.findOne(medicament.getPharmacyId()));
		e.setDone(false);
		e.setDeleted(false);
		e.setPrice(medicament.getPrice());
		PrescriptionMedicament pm = new PrescriptionMedicament();
		pm.setDeleted(false);
		pm.setExpiryDate(medicament.getExpiryDate());
		pm.setMedicament(medicament.getMedicament());
		pm.setQuantity(medicament.getQuantity());
		pm.setPurchased(false);

		if(e.getPrescriptionMedicaments() == null)
			e.setPrescriptionMedicaments(new HashSet<>());


		System.out.println(p.getEmail());
		System.out.println(p.getePrescriptions());
		System.out.println(e.getDate());
		e.getPrescriptionMedicaments().add(pm);
		p.getePrescriptions().add(e);

		try {

			pharmacyService.update(pharmacyService.findOne(medicament.getPharmacyId()));
			e = this.create(e);
			patientService.update(p);
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}

	}

	@Override
	@Transactional
	public ePrescription returnToPharmacyStock(PrescriptionMedicament medicament, int quantity) {
		ePrescription ep = ePrescriptionRepository.findePrescriptionWherePrescriptionMedicament(medicament.getId());

		//System.out.println("aj nesto " + medicament.getId());
		Pharmacy pharmacy = pharmacyRepository.getOneWithMedicamentsPatient(ep.getPharmacy().getId());
		MedicamentItem medicamentItem = pharmacy.getMedicamentItems().stream()
				.filter(mi -> mi.getMedicament().getId().equals(medicament.getId())).findFirst().orElse(null);
				if (medicamentItem != null)
					medicamentItem.setQuantity(medicamentItem.getQuantity() + medicament.getQuantity());
//				pharmacyService.update(pharmacy);
				pharmacyRepository.save(pharmacy);



		return null;
	}

}
