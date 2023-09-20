package com.mrsisa.mrsisaprojekat.service;

import com.mrsisa.mrsisaprojekat.model.MedicalReport;
import com.mrsisa.mrsisaprojekat.repository.MedicalReportRepositoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalReportServiceImpl implements  MedicalReportService {

    @Autowired
    private MedicalReportRepositoryDB medicalReportRepository;

    @Override
    public List<MedicalReport> findAll() {
        return medicalReportRepository.findAll();
    }

    @Override
    public MedicalReport findOne(Long id) {
        System.out.println("hey i get here, id is " + id);
        return medicalReportRepository.findById(id).orElse(null);
    }

    @Override
    public MedicalReport create(MedicalReport medicalReport) throws Exception {
        return null;
    }

    @Override
    public MedicalReport update(MedicalReport medicalReport) throws Exception {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean reportBelongsToPatient(Long id, String email) {
        MedicalReport report = this.findOne(id);

        if (report == null) {
            return false;
        }

        if (report.isDeleted()) {
            return false;
        }

        return report.getEprescription().getPatient().getEmail().equals(email);
    }
}
