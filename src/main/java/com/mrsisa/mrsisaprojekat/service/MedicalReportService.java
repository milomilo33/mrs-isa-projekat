package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.MedicalReport;

import java.util.List;

public interface MedicalReportService {
	List<MedicalReport> findAll();

	MedicalReport findOne(Long id);

	MedicalReport create(MedicalReport medicalReport) throws Exception;

	MedicalReport update(MedicalReport medicalReport) throws Exception;
	
	boolean delete(Long id);

	boolean reportBelongsToPatient(Long id, String email);
}
