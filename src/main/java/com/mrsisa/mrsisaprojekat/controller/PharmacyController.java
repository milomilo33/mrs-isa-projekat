package com.mrsisa.mrsisaprojekat.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.mrsisa.mrsisaprojekat.dto.AddressDTO;
import com.mrsisa.mrsisaprojekat.dto.AppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.DermatologistDTO;
import com.mrsisa.mrsisaprojekat.dto.MedicamentItemDTO;
import com.mrsisa.mrsisaprojekat.dto.MonthAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.OrderDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacistDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.PricelistItemAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.ReportAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.RequestDTO;
import com.mrsisa.mrsisaprojekat.dto.WorkHourDTO;
import com.mrsisa.mrsisaprojekat.dto.ePrescriptionPreviewDTO;
import com.mrsisa.mrsisaprojekat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.AdminPharmacy;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Dermatologist;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.Pharmacist;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemAppointment;
import com.mrsisa.mrsisaprojekat.model.Request;
import com.mrsisa.mrsisaprojekat.model.WorkHour;
import com.mrsisa.mrsisaprojekat.model.ePrescription;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/api/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private ePrescriptionService ePrescriptionService;
	
	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@Autowired
	private ReportService reportService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private MedicamentItemService medicamentService;

	@Autowired
	private AppointmentService appointmentService;


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
			ArrayList<Appointment> list = pharmacyService.findAvailableAppointmentsDeramtologist(m.getEmail(),pharmacy.getId());
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
				d.setAppointments(list);
				d.setRating(dermatologistService.getRating(d.getEmail()));
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
				ArrayList<Appointment> list = pharmacyService.findAvailableAppointmentsPharmacist(p.getEmail(),pharmacy.getId());
				ArrayList<WorkHourDTO> hours = new ArrayList<WorkHourDTO>();
				for(WorkHour h : p.getWorkHour()) {
					WorkHourDTO wd = new WorkHourDTO(h);
					hours.add(wd);
				}
				PharmacistDTO d = new PharmacistDTO(p);
				d.setWorkHours(hours);
				d.setAppointments(list);
				d.setRating(pharmacistService.getRating(d.getEmail()));
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

	
	
	
	@GetMapping(value = "filter/rating={rating}&subscribed={username}")
	public ResponseEntity<Collection<PharmacyDTO>> filterPharmacies(@PathVariable("rating") int rating, @PathVariable("username") String username) {
		Collection<Pharmacy> pharmacies = patientService.filterPharmacy(username, rating);
		Collection<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for(Pharmacy p : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(p));
		}
		return ResponseEntity.ok().body(pharmaciesDTO);
	}
	
	
	@GetMapping(value = "/reportAppointments/{id}/{month}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportAppointments(@PathVariable("id") Long id, @PathVariable("month") String month, @PathVariable("year") String year) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subValues(pharmacy.getAppointments(), appointments, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByDay(appointmentsReturned);
		ArrayList<MonthAppointmentDTO> one = new ArrayList<MonthAppointmentDTO>();
		for(MonthAppointmentDTO mm : all) {
			if(mm.getMonth().name().equalsIgnoreCase(month)) {
				one.add(mm);
			}
		}
		report.setMonthAppoinntments(one);
		
		return new ResponseEntity<>(report, HttpStatus.OK);
	}

	
	@GetMapping(value = "/reportAppointments1/{id}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportAppointments1(@PathVariable("id") Long id, @PathVariable("year") String year) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subValues(pharmacy.getAppointments(), appointments, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByMonth(appointmentsReturned);
		report.setMonthAppoinntments(all);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportAppointments2/{id}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportAppointments2(@PathVariable("id") Long id, @PathVariable("year") String year) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subValues(pharmacy.getAppointments(), appointments, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByMonth(appointmentsReturned);
		ArrayList<MonthAppointmentDTO> quarters = reportService.findByQuarters(all);
		
		report.setMonthAppoinntments(quarters);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/reportMedicaments/{id}/{month}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportMedicaments(@PathVariable("id") Long id, @PathVariable("month") String month, @PathVariable("year") String year) {
		Set<ePrescription> ePrescriptions = ePrescriptionService.findAllePrescriptionsInPharmacy(id);
		Set<ePrescriptionPreviewDTO> dtos = reportService.findePrescriptions(ePrescriptions);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> list = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subValues1(dtos, list, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByDay(appointmentsReturned);
		ArrayList<MonthAppointmentDTO> one = new ArrayList<MonthAppointmentDTO>();
		for(MonthAppointmentDTO mm : all) {
			if(mm.getMonth().name().equalsIgnoreCase(month)) {
				one.add(mm);
			}
		}
		report.setMonthAppoinntments(one);
	
		return new ResponseEntity<>(report,HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportMedicaments1/{id}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportMedicaments1(@PathVariable("id") Long id, @PathVariable("year") String year) {
		Set<ePrescription> ePrescriptions = ePrescriptionService.findAllePrescriptionsInPharmacy(id);
		Set<ePrescriptionPreviewDTO> dtos = reportService.findePrescriptions(ePrescriptions);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> list = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subValues1(dtos, list, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByMonth(appointmentsReturned);
		report.setMonthAppoinntments(all);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	@GetMapping(value = "/reportMedicaments2/{id}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReportMedicaments2(@PathVariable("id") Long id, @PathVariable("year") String year) {
		Set<ePrescription> ePrescriptions = ePrescriptionService.findAllePrescriptionsInPharmacy(id);
		Set<ePrescriptionPreviewDTO> dtos = reportService.findePrescriptions(ePrescriptions);
		
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> list = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subValues1(dtos, list, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByMonth(appointmentsReturned);
		ArrayList<MonthAppointmentDTO> quarters = reportService.findByQuarters(all);
		
		report.setMonthAppoinntments(quarters);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportPharmacy/{id}/{month}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReport(@PathVariable("id") Long id, @PathVariable("month") String month, @PathVariable("year") String year) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ArrayList<PricelistItemAppointmentDTO> items = reportService.findPricelistItemsAppointments(id);
		Set<ePrescription> ePrescriptions = ePrescriptionService.findAllePrescriptionsInPharmacy(id);
		Set<ePrescriptionPreviewDTO> dtos =  reportService.findePrescriptionsPrice( ePrescriptions);
		Set<OrderDTO> ordersInPharmacy = reportService.findAllOrdersInPharmacy(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subPriceAppointments(items,pharmacy.getAppointments(), appointments, dtos, ordersInPharmacy, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByDay(appointmentsReturned);
		
		ArrayList<MonthAppointmentDTO> one = new ArrayList<MonthAppointmentDTO>();
		for(MonthAppointmentDTO mm : all) {
			if(mm.getMonth().name().equalsIgnoreCase(month)) {
				one.add(mm);
			}
		}
		report.setMonthAppoinntments(one);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportPharmacy1/{id}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReport1(@PathVariable("id") Long id, @PathVariable("year") String year) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ArrayList<PricelistItemAppointmentDTO> items = reportService.findPricelistItemsAppointments(id);
		Set<ePrescription> ePrescriptions = ePrescriptionService.findAllePrescriptionsInPharmacy(id);
		Set<ePrescriptionPreviewDTO> dtos =  reportService.findePrescriptionsPrice( ePrescriptions);
		Set<OrderDTO> ordersInPharmacy = reportService.findAllOrdersInPharmacy(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subPriceAppointments(items,pharmacy.getAppointments(), appointments, dtos, ordersInPharmacy, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByMonth(appointmentsReturned);
		report.setMonthAppoinntments(all);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportPharmacy2/{id}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<ReportAppointmentDTO> getPharmacyReport2(@PathVariable("id") Long id, @PathVariable("year") String year) {
		Pharmacy pharmacy = pharmacyService.findOneWithAppointments(id);
		ArrayList<PricelistItemAppointmentDTO> items = reportService.findPricelistItemsAppointments(id);
		Set<ePrescription> ePrescriptions = ePrescriptionService.findAllePrescriptionsInPharmacy(id);
		Set<ePrescriptionPreviewDTO> dtos =  reportService.findePrescriptionsPrice( ePrescriptions);
		Set<OrderDTO> ordersInPharmacy = reportService.findAllOrdersInPharmacy(id);
		ReportAppointmentDTO report = new ReportAppointmentDTO();
		ArrayList<MonthAppointmentDTO> appointments = reportService.makeReport();
		ArrayList<MonthAppointmentDTO> appointmentsReturned = reportService.subPriceAppointments(items,pharmacy.getAppointments(), appointments, dtos, ordersInPharmacy, year);
		ArrayList<MonthAppointmentDTO> all = reportService.findByMonth(appointmentsReturned);
		ArrayList<MonthAppointmentDTO> quarters = reportService.findByQuarters(all);
		report.setMonthAppoinntments(quarters);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/rating/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<Integer> getPharmacyRating(@PathVariable("id") Long id) {
		int rating = pharmacyService.getRating(id);
		return new ResponseEntity<>(rating, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/requests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN')")
	public ResponseEntity<Collection<RequestDTO>> getPharmacyRequests(@PathVariable("id") Long id) {
		Pharmacy pharmacy = pharmacyService.findOneWithRequests(id);
		List<RequestDTO> returns = new ArrayList<>();
		for(Request r : pharmacy.getRequests()) {
				RequestDTO d = new RequestDTO(r);
				returns.add(d);
				
		}
		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returns,HttpStatus.OK);
	}
	@GetMapping(value = "/getAllWithAdmins")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<Collection<PharmacyDTO>> getAllWithAdmins(){
		Collection<Pharmacy> pharmacies = pharmacyService.findAllWithAdmin();
		Collection<PharmacyDTO> pharmaciesDTO = new ArrayList<PharmacyDTO>();
		for(Pharmacy p : pharmacies) {
			AddressDTO address = new AddressDTO(p.getAddress());
			Set<String> admins = new HashSet<String>();
			for(AdminPharmacy a: p.getAdmins()) {
				admins.add(a.getEmail());
			}
			PharmacyDTO pharmacy = new PharmacyDTO(p.getId(), p.getName(), address, admins );
			pharmaciesDTO.add(pharmacy);
		}

		return new ResponseEntity<Collection<PharmacyDTO>>(pharmaciesDTO, HttpStatus.OK);
	}
	@GetMapping(value = "/delete/{id}")
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
	public ResponseEntity<PharmacyDTO> deletePharmacy(@PathVariable("id") Long id) throws Exception{
		pharmacyService.deletePharmacy(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
