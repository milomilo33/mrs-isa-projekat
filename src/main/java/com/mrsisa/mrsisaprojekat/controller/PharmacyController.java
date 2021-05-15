package com.mrsisa.mrsisaprojekat.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.MedicamentItemDTO;
import com.mrsisa.mrsisaprojekat.dto.MonthAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.MonthAppointmentDTO.Quarter;
import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.ReportAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.WorkHourDTO;

import com.mrsisa.mrsisaprojekat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.WorkHour;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/api/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private DermatologistService dermatologistService;

	@Autowired
	private PharmacistService pharmacistService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PharmacyDTO>> getPharmacies(){
		
		Collection<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for(Pharmacy p : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(p));

		}

		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacyDTO> getPharmacy(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOne(id);

		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new PharmacyDTO(pharmacy), HttpStatus.OK);
	}

	@GetMapping(value = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PharmacyDTO>> getPharmacyWhere(@PathVariable("query") String query) {

		Collection<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		pharmacies = pharmacies.stream().filter(p -> ((p.getName() + p.getDescription() +
			p.getAddress().getCountry() + p.getAddress().getCity() + p.getAddress().getStreet() +  p.getAddress().getNumber())
				.toLowerCase().contains(query))).collect(Collectors.toCollection(ArrayList::new));
		for (Pharmacy p : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(p));
		}
		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN')")
	public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) throws Exception {
		Address address = addressService.create(pharmacy.getAddress());
		pharmacy.setAddress(address);
		Pharmacy savedPharmacy = pharmacyService.create(pharmacy);
		return new ResponseEntity<>(savedPharmacy, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/medicamentItems/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<MedicamentItemDTO>> getPharmacyMedicamentItems(@PathVariable("id") Long id) {
		Collection<MedicamentItem> items = pharmacyService.getAllMedicaments(id);
		List<MedicamentItemDTO> returns = new ArrayList<>();
		for(MedicamentItem m: items) {
			if(!m.isDeleted()) {
				returns.add(new MedicamentItemDTO(m));
			}
		}
		if (items == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}
	
	@PutMapping(value= "/updateMedicaments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'PHARMACIST')")
	public ResponseEntity<PharmacyDTO> updatePharmacy(@RequestBody Pharmacy pharmacy,@PathVariable("id") Long id) throws Exception {
		Pharmacy pharmacyUpdate = pharmacyService.findOneWithMedicaments(id);
		for(MedicamentItem m : pharmacy.getMedicaments()) {
			pharmacyUpdate.getMedicaments().add(m);
			
		}
		if (pharmacyUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		pharmacyUpdate = pharmacyService.update(pharmacyUpdate);
		return new ResponseEntity<PharmacyDTO>(new PharmacyDTO(pharmacyUpdate), HttpStatus.OK);
	}
	
	@GetMapping(value = "/dermatologists/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN', 'DERMATOLOGIST')")
	public ResponseEntity<Collection<DermatologistDTO>> getPharmacyDermatologists(@PathVariable("id") String id) {
		Pharmacy pharmacy = pharmacyService.findOneWithDermatologists(Long.parseLong(id));
		List<DermatologistDTO> returns = new ArrayList<>();
		for(Dermatologist m: pharmacy.getDermatologists()) {
			m.setMedicalExaminations(new HashSet<>(dermatologistService.getAvailableAppointments(m)));
			if(!m.isDeleted()) {
				ArrayList<WorkHourDTO> hours = new ArrayList<WorkHourDTO>();
				for(WorkHour h : m.getWorkHour()) {
					if(h.getPharmacy().getId() == pharmacy.getId()) {
						WorkHourDTO wd = new WorkHourDTO(h);
						hours.add(wd);
					}
				}
				DermatologistDTO d = new DermatologistDTO(m);
				d.setWorkHours(hours);
				ArrayList<AppointmentDTO> lista = new ArrayList<AppointmentDTO>();
				for(Appointment a : pharmacyService.findAllAppointmentsDeramtologist(d.getEmail(), pharmacy.getId())) {
					lista.add(new AppointmentDTO(a));
				}
				d.setAllAppointments(lista);
				returns.add(d);
			}
		}
		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}
	
	@GetMapping(value = "/pharmacists/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN', 'PHARMACIST')")
	public ResponseEntity<Collection<PharmacistDTO>> getPharmacyPharmacists(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOneWithPharmacists(id);
		List<PharmacistDTO> returns = new ArrayList<>();
		for(Pharmacist p: pharmacy.getPharmacists()) {
			if(!p.isDeleted()) {
				p.setCounselings(new HashSet<>(pharmacistService.getAvailableAppointments(p)));
				ArrayList<WorkHourDTO> hours = new ArrayList<WorkHourDTO>();
				for(WorkHour h : p.getWorkHour()) {
					WorkHourDTO wd = new WorkHourDTO(h);
					hours.add(wd);
				}
				PharmacistDTO d = new PharmacistDTO(p);
				d.setWorkHours(hours);
				returns.add(d);
			}
		}
		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}
	
	@GetMapping(value = "/appointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SYSTEM_ADMIN', 'PHARMACIST')")
	public ResponseEntity<Collection<AppointmentDTO>> getPharmacyAppointments(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		List<AppointmentDTO> returns = new ArrayList<>();
		for(Appointment a : pharmacy.getAppointments()) {
			if(!a.isDone() && a.getPatient() == null) {
				AppointmentDTO d = new AppointmentDTO(a);
				returns.add(d);
				}
				
		}
		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}

	
	
	
	@GetMapping(value = "filter/rating={rating}")
	public ResponseEntity<Collection<PharmacyDTO>> filterPharmacies(@PathVariable("rating") int rating) {
		Collection<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();

		if(rating != -1) {
			for (Pharmacy p : pharmacies) {

				if (pharmacyService.getRating(p.getId()) == rating)
					pharmaciesDTO.add(new PharmacyDTO(p));
			}
		}

		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}
	public ArrayList<MonthAppointmentDTO> makeReport(){
		ArrayList<MonthAppointmentDTO> set = new ArrayList<MonthAppointmentDTO>();
		for(int i =0;i<11;i++) {
			MonthAppointmentDTO month = new MonthAppointmentDTO();
			month.setMonth(Month.of(i+1));
			HashMap<Integer,Integer> values = new HashMap<Integer,Integer>();
			if(i == 0 || i ==2 || i== 4 || i ==6 || i == 7 ||i==9 || i ==11) {
				for(int j =1;j<32;j++) {
					values.put(j, 0);
				}
				
			}else if(i == 1) {
				if(LocalDateTime.now().getYear()/400 == 0 && (LocalDateTime.now().getYear()/100 !=0 && LocalDateTime.now().getYear()/4 ==0)) {
					for(int j =1;j<30;j++) {
						values.put(j, 0);
					}
				}else {
					for(int j =1;j<29;j++) {
						values.put(j, 0);
					}
				}
			}else {
				for(int j =1;j<31;j++) {
					values.put(j, 0);
				}
			}
			month.setValues(values);
			set.add(month);

		}
		return set;
		
	}
	public ArrayList<MonthAppointmentDTO> subValues(Set<Appointment> pharmacyList,ArrayList<MonthAppointmentDTO> list){
		for(Appointment a : pharmacyList) {
			for(MonthAppointmentDTO aa : list) {
				//is Done fali
				if(a.getDate().getMonth() == aa.getMonth() && aa.getValues().containsKey(a.getDate().getDayOfMonth())) {
					int val = aa.getValues().get(a.getDate().getDayOfMonth());
					aa.getValues().put(a.getDate().getDayOfMonth(),val+1);
				}
			}
			
		}
		
		return list;
	}
	
	@GetMapping(value = "/reportAppointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportAppointments(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = subValues(pharmacy.getAppointments(), appointments);
		
		ArrayList<MonthAppointmentDTO> all = new ArrayList<MonthAppointmentDTO>();
		for(MonthAppointmentDTO m : appointmentsReturned) {
			for(Integer i : m.getValues().keySet()) {
					MonthAppointmentDTO mm = new MonthAppointmentDTO();
					mm.setMonth(m.getMonth());
					mm.setDay(i);
					mm.setValue(m.getValues().get(i));
					all.add(mm);
				}
			}
			
	
		report.setMonthAppoinntments(all);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}

	public ArrayList<MonthAppointmentDTO> findByMonth(ArrayList<MonthAppointmentDTO> appointmentsReturned){
		ArrayList<MonthAppointmentDTO> all = new ArrayList<MonthAppointmentDTO>();
		for(MonthAppointmentDTO m : appointmentsReturned) {
			MonthAppointmentDTO mm = new MonthAppointmentDTO();
			mm.setMonth(m.getMonth());
			int value = 0;
			for(Integer i : m.getValues().keySet()) {
					value += m.getValues().get(i);
				}
			mm.setValue(value);
			all.add(mm);
			}
	return all;
		
	}
	@GetMapping(value = "/reportAppointments1/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportAppointments1(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = subValues(pharmacy.getAppointments(), appointments);
		ArrayList<MonthAppointmentDTO> all = findByMonth(appointmentsReturned);
		report.setMonthAppoinntments(all);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportAppointments2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportAppointments2(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = subValues(pharmacy.getAppointments(), appointments);
		ArrayList<MonthAppointmentDTO> all = findByMonth(appointmentsReturned);
		
		ArrayList<MonthAppointmentDTO> q1 = new ArrayList<MonthAppointmentDTO>();
		ArrayList<MonthAppointmentDTO> q2 = new ArrayList<MonthAppointmentDTO>();
		ArrayList<MonthAppointmentDTO> q3 = new ArrayList<MonthAppointmentDTO>();
		ArrayList<MonthAppointmentDTO> q4 = new ArrayList<MonthAppointmentDTO>();
		ArrayList<MonthAppointmentDTO> quarters = new ArrayList<MonthAppointmentDTO>();
		for(MonthAppointmentDTO m : all) {
			if(m.getMonth().getValue() == 1 || m.getMonth().getValue() ==2 || m.getMonth().getValue() == 3) {
				q1.add(m);
			}else if(m.getMonth().getValue() == 4 || m.getMonth().getValue() ==5 || m.getMonth().getValue() == 6) {
				q2.add(m);
			}else if(m.getMonth().getValue() == 7 || m.getMonth().getValue() ==8 || m.getMonth().getValue() == 9) {
				q3.add(m);
			}else {
				q4.add(m);
			}
		}
		MonthAppointmentDTO ma = new MonthAppointmentDTO();
		ma.setQuarter(Quarter.Q1);
		int value = 0;
		for(MonthAppointmentDTO m: q1) {
			value += m.getValue();
		}
		ma.setValue(value);
		quarters.add(ma);	
		
		MonthAppointmentDTO ma1 = new MonthAppointmentDTO();
		ma1.setQuarter(Quarter.Q2);
		int value1 = 0;
		for(MonthAppointmentDTO m1: q2) {
			value1 += m1.getValue();
			
		}
		ma1.setValue(value1);
		quarters.add(ma1);	
		
		
		MonthAppointmentDTO ma2 = new MonthAppointmentDTO();
		ma2.setQuarter(Quarter.Q3);
		int value2 = 0;
		for(MonthAppointmentDTO m2: q3) {
			value2 += m2.getValue();
			
		}
		ma2.setValue(value2);
		quarters.add(ma2);	
		
		MonthAppointmentDTO ma3 = new MonthAppointmentDTO();
		ma3.setQuarter(Quarter.Q4);
		int value3 = 0;
		for(MonthAppointmentDTO m3: q4) {
			value3 += m3.getValue();
			
		}
		ma3.setValue(value3);
		quarters.add(ma3);	
		report.setMonthAppoinntments(quarters);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
}
