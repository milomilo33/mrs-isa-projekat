package com.mrsisa.mrsisaprojekat.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.dto.ePrescriptionDispenseDTO;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.ePrescription;
import com.mrsisa.mrsisaprojekat.service.ePrescriptionService;

@RestController
@RequestMapping("/api/eprescriptions")
public class ePrescriptionController {
	
	@Autowired
	private ePrescriptionService ePrescriptionService;
	
	@GetMapping(value="/{id}/dispense")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<String> dispensePrescription(@PathVariable("id") Long id) {
		// dobaviti trenutnog farmaceuta pomocu jwt
		// zasad ce se prosledjivati null i nece se u servisu proveravati da li je farmaceut iz te apoteke
		boolean dispensed = ePrescriptionService.dispensePrescription(id, null);
		if (!dispensed) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'PATIENT')")
	public ResponseEntity<ePrescriptionDispenseDTO> getPrescription(@PathVariable("id") Long id) {
		// dobaviti trenutnog farmaceuta pomocu jwt
		// zasad null kao parametar servisne metode
		ePrescription ePrescription = ePrescriptionService.findPrescriptionForPharmacist(id, null);
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
