package com.br.odontoscheduler.service;

import com.br.odontoscheduler.model.Patient;
import com.br.odontoscheduler.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements PatientServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient save(Patient patient) {
        logger.info("saving patient " + patient.getEmail());
        return patientRepository.save(patient);
    }

    @Override
    public Patient getById(Patient patient) {
        return null;
    }
}
