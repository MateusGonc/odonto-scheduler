package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.model.Patient;
import com.br.odontoscheduler.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController extends AbstractController<Patient, PatientService> {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @Override
    public PatientService getService() {
        return this.patientService;
    }

    @GetMapping("/find")
    public ResponseEntity<List<Patient>> getByPattern(@RequestParam String pattern,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        List<Patient> objectList = this.getService().findPatientsByPattern(pattern, page, size);

        return new ResponseEntity<List<Patient>>(objectList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable String id,
            @RequestBody Patient patient) {

        logger.info("updating " + patient.toString());
        Patient updatedPatient = this.getService().updatePatient(id, patient);

        if (updatedPatient != null) {
            return new ResponseEntity<Patient>(updatedPatient, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
