package com.mrsisa.mrsisaprojekat.repository;

import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrescriptionRepositoryDB extends JpaRepository<PrescriptionMedicament, Long> {

    @Query(value = "insert into patient_reserved_medicaments (patient_email, reserved_medicaments_id) values (?1, ?2)", nativeQuery = true)
    void updatePatientReservation(String email, Long id);

}
