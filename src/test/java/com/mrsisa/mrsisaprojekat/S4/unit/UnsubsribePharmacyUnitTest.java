package com.mrsisa.mrsisaprojekat.S4.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

import com.mrsisa.mrsisaprojekat.dto.SubscribedPharmacyDTO;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.Pharmacy;
import com.mrsisa.mrsisaprojekat.repository.PatientRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PharmacyRepositoryDB;

import com.mrsisa.mrsisaprojekat.service.PatientServiceImpl;

@SpringBootTest
public class UnsubsribePharmacyUnitTest {
    
    @Mock
    private PatientRepositoryDB patientRepositoryMock;
    
    @Mock
    private PharmacyRepositoryDB pharmacyRepositoryMock;
    
    @InjectMocks
    private PatientServiceImpl patientServiceMock;
    
    private Pharmacy pharmacyMock;
    
    private Patient patientMock;
    
    private SubscribedPharmacyDTO subscribePharmacyDTOMock;

    
    @BeforeEach
    public void Setup() {
        
        patientMock = new Patient();
        pharmacyMock = new Pharmacy();
        subscribePharmacyDTOMock = new SubscribedPharmacyDTO();
        
        
        pharmacyMock.setId(1L);
        
        Set<Pharmacy> subs = new HashSet<>();
        subs.add(pharmacyMock);
        patientMock.setEmail("example@gmail.com");
        
        subscribePharmacyDTOMock.setPharmacy(pharmacyMock);
        subscribePharmacyDTOMock.setUser("example@gmail.com");
        
        
    }
    
    
        @Test
        @Transactional
        @Rollback
        public void testValidUnsubscription() {
                     
            Mockito.doReturn(Optional.of(patientMock)).when(patientRepositoryMock).findById("example@gmail.com");
        	SubscribedPharmacyDTO subss  = patientServiceMock.unsubsribe(subscribePharmacyDTOMock);
        	assertEquals(subss, subscribePharmacyDTOMock);
        }

        @Test
        @Transactional
        @Rollback
        public void testInvalidUsername() {
            Mockito.doReturn(Optional.of(patientMock)).when(patientRepositoryMock).findById("example@gmail.com");
            Mockito.doReturn(null).when(patientRepositoryMock).findAllSubscribedPharmacies("example@gmail.com");
            
            SubscribedPharmacyDTO subss  = patientServiceMock.unsubsribe(subscribePharmacyDTOMock);
        	assertEquals(subss, null);
        }
        
        
    
        
    
    
    

}