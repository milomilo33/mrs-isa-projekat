package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.ePrescriptionDispenseDTO;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.ePrescription;
import com.mrsisa.mrsisaprojekat.service.ePrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/eprescriptions")
public class ePrescriptionController {
	
	@Autowired
	private ePrescriptionService ePrescriptionService;
	
	@GetMapping(value="/{id}/dispense")
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	public ResponseEntity<String> dispensePrescription(@PathVariable("id") Long id) {
		Pharmacist currentPharmacist = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean dispensed = ePrescriptionService.dispensePrescription(id, currentPharmacist);
		if (!dispensed) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/dispensable", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	public ResponseEntity<ePrescriptionDispenseDTO> getPrescriptionForDispensation(@PathVariable("id") Long id) {
		Pharmacist currentPharmacist = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ePrescription ePrescription = ePrescriptionService.findPrescriptionForPharmacist(id, currentPharmacist);
		if (ePrescription == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// funkcionalno!
//		ePrescription.getPatient().setAllergies(null);
//		ePrescription.getPatient().setAppointments(null);
//		ePrescription.getPatient().setComplaints(null);
//		ePrescription.getPatient().setePrescriptions(null);
//		ePrescription.getPatient().setSubscribedPharmacies(null);
//		ePrescription.setPharmacy(null);
//		for (PrescriptionMedicament pm : ePrescription.getPrescriptionMedicaments()) {
//			pm.getMedicament().setRatings(null);
//			pm.getMedicament().setSubstituteMedicaments(null);
//		}
//		ePrescription = (ePrescription) Hibernate.unproxy(ePrescription);
//		ePrescription.setPatient((Patient) Hibernate.unproxy(ePrescription.getPatient()));
//		ePrescription.setPrescriptionMedicaments((Set<PrescriptionMedicament>) Hibernate.unproxy(ePrescription.getPrescriptionMedicaments()));
//		Set<PrescriptionMedicament> newSet = new HashSet<PrescriptionMedicament>();
//		for (PrescriptionMedicament pm : ePrescription.getPrescriptionMedicaments()) {
//			PrescriptionMedicament newSetItem = (PrescriptionMedicament) Hibernate.unproxy(pm);
//			newSetItem.setMedicament((Medicament) Hibernate.unproxy(newSetItem.getMedicament()));
//			newSet.add(newSetItem);
//		}
//		ePrescription.setPrescriptionMedicaments(newSet);
//		return new ResponseEntity<ePrescription>(ePrescription, HttpStatus.OK);

		String patientEmail = ePrescription.getPatient().getEmail();
		LocalDate expiryDate = ePrescription.getDate();
		Map<String, Integer> medicineQuantity = new HashMap<String, Integer>();
		for (PrescriptionMedicament pm : ePrescription.getPrescriptionMedicaments()) {
			medicineQuantity.put(pm.getMedicament().getName(), pm.getQuantity());
		}
		ePrescriptionDispenseDTO dto = new ePrescriptionDispenseDTO(id, patientEmail, medicineQuantity, expiryDate);
		return new ResponseEntity<ePrescriptionDispenseDTO>(dto, HttpStatus.OK);
	}
	
}
