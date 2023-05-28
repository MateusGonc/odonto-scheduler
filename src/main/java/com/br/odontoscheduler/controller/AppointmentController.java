package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.dto.BaseResponse;
import com.br.odontoscheduler.model.Appointment;
import com.br.odontoscheduler.model.User;
import com.br.odontoscheduler.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/appointment")
public class AppointmentController extends AbstractController<Appointment, AppointmentService> {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public AppointmentService getService() {
        return this.appointmentService;
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal User user, @PathVariable String id,
            @RequestBody Appointment appointment) {
        BaseResponse baseResponse = null;
        
        try {
            logger.info("updating " + appointment.toString());
            boolean updated = this.getService().updateAppointment(id, appointment);

            if (updated) {
                baseResponse = new BaseResponse(BaseResponse.MESSAGE_SUCCESS + id, HttpStatus.OK.toString());
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            }

            baseResponse = new BaseResponse(BaseResponse.MESSAGE_NOT_FOUND + id, HttpStatus.NOT_FOUND.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.info("Erro updating " + appointment.toString() + " " + e);
            baseResponse = new BaseResponse(BaseResponse.MESSAGE_ERROR,
                    HttpStatus.BAD_REQUEST.toString());

            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
