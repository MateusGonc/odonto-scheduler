package com.br.odontoscheduler.service;

import com.br.odontoscheduler.model.Patient;
import com.br.odontoscheduler.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PatientService extends AbstractService<Patient, PatientRepository>{

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientRepository getRepository() {
        return this.patientRepository;
    }

    public boolean updatePatient(String id, Patient patient){
        Optional<Patient> optionalPatient = this.patientRepository.findById(id);
        if(optionalPatient.isPresent()){
            Patient currentPatient = optionalPatient.get();
            currentPatient.setUpdatedAt(LocalDateTime.now());
            currentPatient.setAge(patient.getAge());
            currentPatient.setAddress(patient.getAddress());
            currentPatient.setEmail(patient.getEmail());
            currentPatient.setAge(patient.getAge());
            currentPatient.setDocument(patient.getDocument());
            currentPatient.setPhoneNumber(patient.getPhoneNumber());
            currentPatient.setName(patient.getName());
            this.patientRepository.save(currentPatient);
            return true;
        }
        return false;
    }
}
