package com.mrsisa.mrsisaprojekat.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsisa.mrsisaprojekat.dto.MedicamentInePrescriptionDTO;
import com.mrsisa.mrsisaprojekat.dto.MonthAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.OfferDTO;
import com.mrsisa.mrsisaprojekat.dto.PricelistItemAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.ePrescriptionPreviewDTO;
import com.mrsisa.mrsisaprojekat.dto.MonthAppointmentDTO.Quarter;
import com.mrsisa.mrsisaprojekat.dto.OrderDTO;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.Offer;
import com.mrsisa.mrsisaprojekat.model.OfferStatus;
import com.mrsisa.mrsisaprojekat.model.Order;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemAppointment;
import com.mrsisa.mrsisaprojekat.model.ePrescription;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private PricelistItemAppointmentService pricelistItemAppointmentService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OfferService offerService;

	@Override
	public ArrayList<MonthAppointmentDTO> makeReport() {
		ArrayList<MonthAppointmentDTO> set = new ArrayList<MonthAppointmentDTO>();
		for(int i =0;i<12;i++) {
			MonthAppointmentDTO month = new MonthAppointmentDTO();
			month.setMonth(Month.of(i+1));
			HashMap<Integer,Integer> values = new HashMap<Integer,Integer>();
			if(i == 0 || i ==2 || i== 4 || i ==5 || i == 6 ||i==7 || i ==9 || i ==11) {
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

	@Override
	public ArrayList<MonthAppointmentDTO> subValues(Set<Appointment> pharmacyList,ArrayList<MonthAppointmentDTO> list,String year ){
		int y = Integer.parseInt(year);
			for(Appointment a : pharmacyList) {
				for(MonthAppointmentDTO aa : list) {
					//is Done fali
					if(a.getDate().getMonth() == aa.getMonth() && aa.getValues().containsKey(a.getDate().getDayOfMonth()) && a.getDate().getYear() == y) {
						int val = aa.getValues().get(a.getDate().getDayOfMonth());
						aa.getValues().put(a.getDate().getDayOfMonth(),val+1);
					}
				}
				
			}
			
			return list;
	}
	@Override
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
	
	@Override
	public ArrayList<MonthAppointmentDTO> subValues1(Set<ePrescriptionPreviewDTO> dtos,ArrayList<MonthAppointmentDTO> list, String year){
		int y = Integer.parseInt(year);
		for(ePrescriptionPreviewDTO a : dtos) {
			for(MedicamentInePrescriptionDTO pmd: a.getMedicine())
				for(MonthAppointmentDTO aa : list) {
					if(a.getExpiryDate().getMonth() == aa.getMonth() && aa.getValues().containsKey(a.getExpiryDate().getDayOfMonth()) && a.getExpiryDate().getYear() == y) {
						int val = aa.getValues().get(a.getExpiryDate().getDayOfMonth());
						aa.getValues().put(a.getExpiryDate().getDayOfMonth(),val+ pmd.getQuantity());
				}
			}
			
		}
		
		return list;
	}

	@Override
	public ArrayList<MonthAppointmentDTO> findByDay(ArrayList<MonthAppointmentDTO> appointmentsReturned) {
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
			return all;
	}
	
	@Override
	public ArrayList<MonthAppointmentDTO> findByQuarters(ArrayList<MonthAppointmentDTO> all){
		ArrayList<MonthAppointmentDTO> q1 = new ArrayList<MonthAppointmentDTO>();
		ArrayList<MonthAppointmentDTO> q2 = new ArrayList<MonthAppointmentDTO>();
		ArrayList<MonthAppointmentDTO> q3 = new ArrayList<MonthAppointmentDTO>();
		ArrayList<MonthAppointmentDTO> q4 = new ArrayList<MonthAppointmentDTO>();
		ArrayList<MonthAppointmentDTO> quarters = new ArrayList<MonthAppointmentDTO>();
		for(MonthAppointmentDTO m : all) {
			if(m.getMonth().getValue() == 1 || m.getMonth().getValue() == 2 || m.getMonth().getValue() == 3) {
				q1.add(m);
			}else if(m.getMonth().getValue() == 4 || m.getMonth().getValue() == 5 || m.getMonth().getValue() == 6) {
				q2.add(m);
			}else if(m.getMonth().getValue() == 7 || m.getMonth().getValue() == 8 || m.getMonth().getValue() == 9) {
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
		return quarters;
	}

	@Override
	public Set<ePrescriptionPreviewDTO> findePrescriptions(Set<ePrescription> ePrescriptions) {
		Set<ePrescriptionPreviewDTO> dtos = new HashSet<ePrescriptionPreviewDTO>();
		for(ePrescription e: ePrescriptions) {
			ePrescriptionPreviewDTO preview = new ePrescriptionPreviewDTO();
			preview.setExpiryDate(e.getTakenDate());
			preview.setId(e.getId());
			Set<MedicamentInePrescriptionDTO> set = new HashSet<MedicamentInePrescriptionDTO>();
			for(PrescriptionMedicament m : e.getPrescriptionMedicaments()) {
				//if(m.isPurchased()) {
					MedicamentInePrescriptionDTO mp = new MedicamentInePrescriptionDTO(m);
					set.add(mp);
				//}
			}
			preview.setMedicine(set);
			dtos.add(preview);
			
		}
		return dtos;
	}
	
	
	@Override
	public ArrayList<PricelistItemAppointmentDTO> findPricelistItemsAppointments(Long id){
		Set<PricelistItemAppointment> items = (Set<PricelistItemAppointment>) pricelistItemAppointmentService.findAllPharmacy(id);
		ArrayList<PricelistItemAppointmentDTO> list = new ArrayList<>();
	
		// samo aktivne cene (odnosne one kod kojih je deleted = false)
		for(PricelistItemAppointment p :items) {
			ArrayList<Price> prices = new ArrayList<>();
			for(Price pp : p.getPrice()) {
				if(pp.isDeleted()) {
					continue;	
				}
				prices.add(pp);
			}
	
		PricelistItemAppointmentDTO pmdt = new PricelistItemAppointmentDTO(p);
		pmdt.setPrice(prices);
		list.add(pmdt);
	}
		return list;
	
	}

	@Override
	public ArrayList<MonthAppointmentDTO> subPriceAppointments(ArrayList<PricelistItemAppointmentDTO> l,
			Set<Appointment> pharmacyList, ArrayList<MonthAppointmentDTO> list, Set<ePrescriptionPreviewDTO> ePrescriptions, Set<OrderDTO> ordersInPharmacy, String year) {
		int y = Integer.parseInt(year);
		for(Appointment a : pharmacyList) {
			for(MonthAppointmentDTO aa : list) {
				for(PricelistItemAppointmentDTO p: l) {
				//is Done fali
				if(a.getDate().getMonth() == aa.getMonth() && aa.getValues().containsKey(a.getDate().getDayOfMonth()) && a.getType() == p.getApointment() && a.getDate().getYear() == y) {
					int val = aa.getValues().get(a.getDate().getDayOfMonth());
					aa.getValues().put(a.getDate().getDayOfMonth(),(int)(val+p.getPrice().get(0).getValue()));
					}
				}
			}
			
		}
		for(ePrescriptionPreviewDTO a : ePrescriptions) {
				for(MonthAppointmentDTO aa : list) {
					if(a.getExpiryDate().getMonth() == aa.getMonth() && aa.getValues().containsKey(a.getExpiryDate().getDayOfMonth()) && a.getExpiryDate().getYear() == y) {
						int val = aa.getValues().get(a.getExpiryDate().getDayOfMonth());
						aa.getValues().put(a.getExpiryDate().getDayOfMonth(),(int)(val+ a.getPrice()));
				}
			}
			
		}
		for(OrderDTO o : ordersInPharmacy) {
				for(OfferDTO oo : o.getOffers()) {
					for(MonthAppointmentDTO aa : list) {
						if(oo.getDeadline().getMonth() == aa.getMonth() && aa.getValues().containsKey(oo.getDeadline().getDayOfMonth()) && oo.getDeadline().getYear() == y) {
							int val = aa.getValues().get(oo.getDeadline().getDayOfMonth());
							aa.getValues().put(oo.getDeadline().getDayOfMonth(),(int)(val- oo.getTotalPrice()));
					}
					
					
				}
				
			}
		}
		
		
		return list;
	}

	@Override
	public Set<ePrescriptionPreviewDTO> findePrescriptionsPrice(Set<ePrescription> ePrescriptions) {
		Set<ePrescriptionPreviewDTO> dtos = new HashSet<ePrescriptionPreviewDTO>();
		for(ePrescription e: ePrescriptions) {
			ePrescriptionPreviewDTO preview = new ePrescriptionPreviewDTO();
			preview.setExpiryDate(e.getTakenDate());
			preview.setId(e.getId());
			preview.setPrice(e.getPrice());
			dtos.add(preview);
			
		}
		return dtos;
	}

	@Override
	public Set<OrderDTO> findAllOrdersInPharmacy(Long id) {
		Set<Order> orders = orderService.findAllOrders();
		Set<OrderDTO> ordersPharmacy = new HashSet<OrderDTO>();
		for(Order o : orders) {
			if(o.getAdmin().getPharmacy().getId() == id) {
				Set<Offer> offers = offerService.offersForOrder(o.getId());
				Set<OfferDTO> offersDTO = new HashSet<OfferDTO>();
				for(Offer oo : offers) {
					if(oo.getStatus().equals(OfferStatus.ACCEPTED)) {
						Offer offer = offerService.findOffer(oo.getId());
						OfferDTO od = new OfferDTO();
						od.setDeadline(offer.getAcceptedDate());
						od.setId(offer.getId());
						od.setTotalPrice(offer.getTotalPrice());
						offersDTO.add(od);
					}
					
				}
				OrderDTO op = new OrderDTO(o);
				op.setOffers(offersDTO);
				ordersPharmacy.add(op);
			}
		}
		return ordersPharmacy;
	}
}
