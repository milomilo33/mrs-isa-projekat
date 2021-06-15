package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.dto.PrescriptionMedicamentDTO;
import com.mrsisa.mrsisaprojekat.dto.QRCodePharmacyDTO;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.ePrescription;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ePrescriptionService {
	List<ePrescription> findAll();
	
	ePrescription findOne(Long id);
	
	ePrescription create(ePrescription ePrescription) throws Exception;
	
	ePrescription update(ePrescription ePrescription) throws Exception;
	
	boolean delete(Long id);
	
	boolean dispensePrescription(Long id, String pharmacistEmail);
	
	ePrescription findPrescriptionForPharmacist(Long id, Pharmacist pharmacist);

	Collection<PrescriptionMedicamentDTO> getPrescriptionMedicamentsForMedicalReport(Long medicalReportId);
	
	Set<ePrescription> findAllePrescriptionsInPharmacy(Long id);

	QRCodePharmacyDTO createePrescription(QRCodePharmacyDTO dto);

    boolean createePrescriptionPatient(PrescriptionMedicamentDTO medicament);

	ePrescription returnToPharmacyStock(PrescriptionMedicament medicament, int quantity);
}
