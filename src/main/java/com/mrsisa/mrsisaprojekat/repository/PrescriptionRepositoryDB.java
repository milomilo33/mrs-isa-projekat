package com.mrsisa.mrsisaprojekat.repository;

import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

import javax.transaction.Transactional;

public interface PrescriptionRepositoryDB extends JpaRepository<PrescriptionMedicament, Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into patient_reserved_medicaments (patient_email, reserved_medicaments_id) values (:email, :id)", nativeQuery = true)
    void updatePatientReservation(@Param("email") String email, @Param("id") Long id);
    

}
