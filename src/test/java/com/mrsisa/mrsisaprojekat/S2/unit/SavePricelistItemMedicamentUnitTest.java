package com.mrsisa.mrsisaprojekat.S2.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.dto.MedicamentDTO;
import com.mrsisa.mrsisaprojekat.dto.PharmacyDTO;
import com.mrsisa.mrsisaprojekat.dto.PricelistItemMedicamentDTO;
import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.model.Price;
import com.mrsisa.mrsisaprojekat.model.PricelistItemMedicament;
import com.mrsisa.mrsisaprojekat.repository.MedicamentRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PriceRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PricelistItemMedicamentRepositoryDB;
import com.mrsisa.mrsisaprojekat.service.PricelistItemMedicamentServiceImpl;

@SpringBootTest
public class SavePricelistItemMedicamentUnitTest {

	@Mock
	private MedicamentRepositoryDB medicamentRepositoryMock;
	
	@Mock
	private PriceRepositoryDB priceRepositoryDBMock;
	
	@Mock
	private PricelistItemMedicamentRepositoryDB pricelisItemRepositoryMock;
	
	@Mock
	private PharmacyRepositoryDB pharmacyRepositoryMock;
	
	@InjectMocks
	private PricelistItemMedicamentServiceImpl pricelistItemServiceMock;
	
	private Pharmacy pharmacyMock;
	
	private Price priceMock;
	
	private PricelistItemMedicament pricelistItemMedicamentMock;
	
	private PricelistItemMedicamentDTO pricelistItemMedicamentDTOMock;
	
	private PharmacyDTO pharmacyDTOMock;
	
	private MedicamentDTO medicamentDTOMock;
	
	private Medicament medicamentMock;
	
	
	@BeforeEach
	public void Setup() {
		
		medicamentMock = new Medicament();
		pricelistItemMedicamentMock = new PricelistItemMedicament();
		priceMock = new Price();
		pharmacyMock = new Pharmacy();
		pricelistItemMedicamentDTOMock = new PricelistItemMedicamentDTO();
		medicamentDTOMock = new MedicamentDTO();
		pharmacyDTOMock = new PharmacyDTO();
		
		pharmacyMock.setId(1L);
		
		
		medicamentMock.setId(2L);
		
		priceMock.setId(3L);
		priceMock.setValue(500);
		priceMock.setDateFrom(LocalDate.now());
		priceMock.setDateTo(null);
		priceMock.setDeleted(false);
		priceMock.setPoints(1);
		priceMock.setPromotion(false);
		
		pricelistItemMedicamentMock.setId(4L);
		Set<Price> prices = new HashSet<>();
	    prices.add(priceMock);
		pricelistItemMedicamentMock.setPrice(prices);
		pricelistItemMedicamentMock.setMedicament(medicamentMock);
		pricelistItemMedicamentMock.setPharmacy(pharmacyMock);	
		
		
		medicamentDTOMock.setId(medicamentMock.getId());
		pharmacyDTOMock.setId(pharmacyMock.getId());
		
		pricelistItemMedicamentDTOMock.setId(pricelistItemMedicamentMock.getId());
		pricelistItemMedicamentDTOMock.setPharmacy(pharmacyDTOMock);
		
		ArrayList<Price> pricesDTO = new ArrayList<>();
	    pricesDTO.add(priceMock);
	    
		pricelistItemMedicamentDTOMock.setMedicament(medicamentDTOMock);
		pricelistItemMedicamentDTOMock.setPrice(pricesDTO);
		
	}
	
	 public PricelistItemMedicament simulateSavePricelistItemMedicament(PricelistItemMedicament pricelistItem) {
		 	pricelistItem.setId(4L);
	        return pricelistItem;
	    }
	
		@Test
	    @Transactional
	    @Rollback
	    public void testValidPriceValue() {
			 Mockito.doReturn(Optional.of(priceMock)).when(priceRepositoryDBMock).findById(3L);
			 Mockito.doReturn(Optional.of(pharmacyMock)).when(pharmacyRepositoryMock).findById(1L);
			 Mockito.doReturn(Optional.of(medicamentMock)).when(medicamentRepositoryMock).findById(2L);
	         Mockito.when(pricelisItemRepositoryMock.save(Mockito.any(PricelistItemMedicament.class))).thenAnswer(i -> simulateSavePricelistItemMedicament((PricelistItemMedicament) i.getArguments()[0]));

	        PricelistItemMedicament pMed = pricelistItemServiceMock.savePricelistItemMedicament(pricelistItemMedicamentDTOMock);
	        assertEquals(pricelistItemMedicamentMock.getId(), pMed.getId());
	    }

	
		@Test
	    @Transactional
	    @Rollback
	    public void testInvalidPriceValue() {
			priceMock.setValue(-100);
			ArrayList<Price> pricesDTO = new ArrayList<>();
		    pricesDTO.add(priceMock);
		    
			
			pricelistItemMedicamentDTOMock.setPrice(pricesDTO);
			
			Mockito.doReturn(Optional.of(priceMock)).when(priceRepositoryDBMock).findById(3L);
			Mockito.doReturn(Optional.of(pharmacyMock)).when(pharmacyRepositoryMock).findById(1L);
			Mockito.doReturn(Optional.of(medicamentMock)).when(medicamentRepositoryMock).findById(2L);
	        Mockito.when(pricelisItemRepositoryMock.save(Mockito.any(PricelistItemMedicament.class))).thenAnswer(i -> simulateSavePricelistItemMedicament((PricelistItemMedicament) i.getArguments()[0]));

	        PricelistItemMedicament pMed = pricelistItemServiceMock.savePricelistItemMedicament(pricelistItemMedicamentDTOMock);
	        assertNull(pMed);
	    }
	
	
}
