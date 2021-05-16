package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.repository.PrescriptionRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class PrescriptionMedicamentServiceImpl implements PrescriptionMedicamentService {
    @Autowired
    private PrescriptionRepositoryDB prescriptionRepository;

//    @Autowired
//    private ReserveMedicamentRepositoryDB reserveRepository;

    @Override
    public Collection<PrescriptionMedicament> findAll() {
        return prescriptionRepository.findAll();
    }

    @Override
    public PrescriptionMedicament findOne(Long id) {
        return prescriptionRepository.findById(id).orElseGet(null);
    }

    @Override
    public void create(PrescriptionMedicament medicamentItem) throws Exception {
        prescriptionRepository.save(medicamentItem);
    }

    @Override
    public PrescriptionMedicament update(PrescriptionMedicament medicamentItem) throws Exception {
        PrescriptionMedicament medToUpdate = prescriptionRepository.findById(medicamentItem.getId()).orElseGet(null);
        if (medToUpdate == null) {
            return null;
        }

        return prescriptionRepository.save(medicamentItem);
    }

    @Override
    public void delete(Long id) {
        PrescriptionMedicament medicament = prescriptionRepository.findById(id).orElseGet(null);
        medicament.setDeleted(true);

        prescriptionRepository.save(medicament);
    }

	@Override
	public Set<PrescriptionMedicament> findInePrescrition(Long id) {
		
		return null;
	}
}
