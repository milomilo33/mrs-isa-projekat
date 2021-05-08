package com.mrsisa.mrsisaprojekat.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrsisa.mrsisaprojekat.model.Medicament;
import com.mrsisa.mrsisaprojekat.model.MedicamentItem;
import com.mrsisa.mrsisaprojekat.repository.MedicamentItemRepositoryDB;

@Service
@Transactional(readOnly = true)
public class MedicamentItemServiceImpl implements MedicamentItemService{
	
	@Autowired
	private MedicamentItemRepositoryDB medicamentItemRepository;

	@Override
	public Collection<MedicamentItem> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicamentItem findOne(Long id) {
		MedicamentItem m = medicamentItemRepository.findMedicamentItem(id);
		if(m == null) {
			return null;
		}
		return m;
	}

	@Override
	public MedicamentItem create(MedicamentItem medicamentItem) throws Exception {
		return medicamentItemRepository.save(medicamentItem);
	
	}

	@Override
	public MedicamentItem update(MedicamentItem medicamentItem) throws Exception {
		return medicamentItemRepository.save(medicamentItem);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(Long id) {
		// provera da li ej rezervisan?
		medicamentItemRepository.deleteOne(id);
		
	}

	@Override
	public boolean find(Medicament m) {
		List<MedicamentItem> medicamentItem = medicamentItemRepository.findAllMedicamentItems();
		for(MedicamentItem mI : medicamentItem) {
			if(mI.getMedicament().getName().equals(m.getName()) && mI.getMedicament().isDeleted()) {
				return true;
			}
		}
		return false;
	
	}
	@Transactional(readOnly = false)
	@Override
	public void restore(Long id) {
		medicamentItemRepository.restoreOne(id);
		
	}}