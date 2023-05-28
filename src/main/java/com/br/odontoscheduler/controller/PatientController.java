package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.dto.BaseResponse;
import com.br.odontoscheduler.model.Patient;
import com.br.odontoscheduler.model.User;
import com.br.odontoscheduler.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal User user, @PathVariable String id,
            @RequestBody Patient patient) {
        BaseResponse baseResponse = null;

        try {
            logger.info("updating " + patient.toString());
            boolean updated = this.getService().updatePatient(id, patient);

            if (updated) {
                baseResponse = new BaseResponse(BaseResponse.MESSAGE_SUCCESS + id, HttpStatus.OK.toString());
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            }

            baseResponse = new BaseResponse(BaseResponse.MESSAGE_NOT_FOUND + id, HttpStatus.NOT_FOUND.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.info("Erro saving " + patient.toString() + " " + e);
            baseResponse = new BaseResponse(BaseResponse.MESSAGE_ERROR,
                    HttpStatus.BAD_REQUEST.toString());

            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
