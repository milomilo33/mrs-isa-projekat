package com.mrsisa.mrsisaprojekat.service;

import java.util.ArrayList;
import java.util.Set;

import com.mrsisa.mrsisaprojekat.dto.MonthAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.OrderDTO;
import com.mrsisa.mrsisaprojekat.dto.PricelistItemAppointmentDTO;
import com.mrsisa.mrsisaprojekat.dto.ePrescriptionPreviewDTO;
import com.mrsisa.mrsisaprojekat.model.Appointment;
import com.mrsisa.mrsisaprojekat.model.PricelistItemAppointment;
import com.mrsisa.mrsisaprojekat.model.ePrescription;

public interface ReportService {
	
	 ArrayList<MonthAppointmentDTO> makeReport();
	    
	 ArrayList<MonthAppointmentDTO> subValues(Set<Appointment> pharmacyList,ArrayList<MonthAppointmentDTO> list);
	 
	 ArrayList<MonthAppointmentDTO> findByMonth(ArrayList<MonthAppointmentDTO> appointmentsReturned);
	 
	 ArrayList<MonthAppointmentDTO> subValues1(Set<ePrescriptionPreviewDTO> dtos,ArrayList<MonthAppointmentDTO> list);
	 
	 ArrayList<MonthAppointmentDTO> findByDay(ArrayList<MonthAppointmentDTO> appointmentsReturned);
	 
	 ArrayList<MonthAppointmentDTO> findByQuarters(ArrayList<MonthAppointmentDTO> all);
	 
	 Set<ePrescriptionPreviewDTO>  findePrescriptions(Set<ePrescription> ePrescriptions);
	 
	 ArrayList<PricelistItemAppointmentDTO> findPricelistItemsAppointments(Long id);
	 
	 ArrayList<MonthAppointmentDTO>  subPriceAppointments(ArrayList<PricelistItemAppointmentDTO>l,Set<Appointment> pharmacyList,ArrayList<MonthAppointmentDTO> list, Set<ePrescriptionPreviewDTO> ePrescriptions, Set<OrderDTO> ordersInPharmacy);

	 Set<ePrescriptionPreviewDTO>  findePrescriptionsPrice(Set<ePrescription> ePrescriptions);
	 
	 Set<OrderDTO> findAllOrdersInPharmacy(Long id);
}
