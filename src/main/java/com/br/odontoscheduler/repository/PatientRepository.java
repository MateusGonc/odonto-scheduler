package com.br.odontoscheduler.repository;

import com.br.odontoscheduler.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.regex.Pattern;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

    Page<Patient> findByEmailRegex(Pattern pattern, Pageable pageable);
    Page<Patient> findByFullNameRegex(Pattern pattern, Pageable pageable);
    Page<Patient> findByDocumentRegex(String pattern, Pageable pageable);
}
