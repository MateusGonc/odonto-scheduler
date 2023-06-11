package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.model.Appointment;
import com.br.odontoscheduler.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController extends AbstractController<Appointment, AppointmentService> {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public AppointmentService getService() {
        return this.appointmentService;
    }

    @PutMapping
    public ResponseEntity<Appointment> update(@PathVariable String id,
            @RequestBody Appointment appointment) {

        logger.info("updating " + appointment.toString());
        Appointment updatedAppointment = this.getService().updateAppointment(id, appointment);

        if (updatedAppointment != null) {
            return new ResponseEntity<Appointment>(updatedAppointment, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
