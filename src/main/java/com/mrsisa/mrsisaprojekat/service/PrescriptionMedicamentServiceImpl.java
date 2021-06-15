package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.model.PrescriptionMedicament;
import com.mrsisa.mrsisaprojekat.repository.MedicamentItemRepositoryDB;
import com.mrsisa.mrsisaprojekat.repository.PrescriptionRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
public class PrescriptionMedicamentServiceImpl implements PrescriptionMedicamentService {
    @Autowired
    private PrescriptionRepositoryDB prescriptionRepository;

    @Autowired
    private MedicamentItemRepositoryDB medicamentItemRepository;

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
    public PrescriptionMedicament create(PrescriptionMedicament medicamentItem) throws Exception {
        return prescriptionRepository.save(medicamentItem);
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
    @Transactional
    public void delete(Long id, int amount) {
        PrescriptionMedicament medicament = prescriptionRepository.findById(id).orElseGet(null);
        medicament.setDeleted(true);

        MedicamentItem medicamentItem = medicamentItemRepository.findById(id).orElse(null);
//        if(medicamentItem != null) {
//            medicamentItem.setQuantity(medicamentItem.getQuantity() + amount);
//        }
        prescriptionRepository.save(medicament);
    }

	@Override
	public Set<PrescriptionMedicament> findInePrescrition(Long id) {
		
		return null;
	}
}
