package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.model.Patient;
import com.br.odontoscheduler.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Patient patient){
        try{
            return new ResponseEntity<>(this.patientService.save(patient), HttpStatus.CREATED);
        } catch (Exception e){
            logger.info("Erro saving patient " + patient.getDocument() + " " + e);
            return new ResponseEntity<>("Patient had errors to save.",HttpStatus.BAD_REQUEST);
        }

    }
}
