package com.br.odontoscheduler.service;

import com.br.odontoscheduler.model.Patient;
import com.br.odontoscheduler.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PatientService extends AbstractService<Patient, PatientRepository> {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientRepository getRepository() {
        return this.patientRepository;
    }

    public List<Patient> findPatientsByPattern(String pattern, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        String regexPattern = "(?i)" + pattern;  // Add "(?i)" to make the pattern case-insensitive
        Pattern compiledPattern = Pattern.compile(regexPattern);

        Page<Patient> pagePatientEmail = this.patientRepository.findByEmailRegex(compiledPattern,pageable);
        Page<Patient> pagePatientName = this.patientRepository.findByFullNameRegex(compiledPattern,pageable);
        Page<Patient> pagePatientDoc = this.patientRepository.findByDocumentRegex(pattern,pageable);

        List<Patient> listPatient = new ArrayList<>();
        listPatient.addAll(pagePatientEmail.getContent());

        for(Patient patient: pagePatientName.getContent()){
            if(!listPatient.contains(patient)){
                listPatient.add(patient);
            }
        }

        for(Patient patient: pagePatientDoc.getContent()){
            if(!listPatient.contains(patient)){
                listPatient.add(patient);
            }
        }

        return listPatient;
    }

    public Patient updatePatient(String id, Patient patient) {
        Optional<Patient> optionalPatient = this.patientRepository.findById(id);

        if (optionalPatient.isPresent()) {
            Patient currentPatient = optionalPatient.get();
            currentPatient.setUpdatedAt(LocalDateTime.now());
            currentPatient.setBirthDate(patient.getBirthDate());
            currentPatient.setAddress(patient.getAddress());
            currentPatient.setEmail(patient.getEmail());
            currentPatient.setDocument(patient.getDocument());
            currentPatient.setPhoneNumber(patient.getPhoneNumber());
            currentPatient.setGender(patient.getGender());
            currentPatient.setNotes(patient.getNotes());
            currentPatient.setFullName(patient.getFullName());

            return this.patientRepository.save(currentPatient);
        }

        return null;
    }
}
