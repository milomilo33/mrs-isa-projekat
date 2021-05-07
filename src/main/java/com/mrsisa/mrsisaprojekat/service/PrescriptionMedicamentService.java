package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;

import java.util.Collection;

public interface PrescriptionMedicamentService {
    Collection<PrescriptionMedicament> findAll();

    PrescriptionMedicament findOne(Long id);

    void create(PrescriptionMedicament medicamentItem) throws Exception;

    PrescriptionMedicament update(PrescriptionMedicament medicamentItem) throws Exception;

    void delete(Long id);

}
