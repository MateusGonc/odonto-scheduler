package com.br.odontoscheduler.service;

import com.br.odontoscheduler.model.Appointment;
import com.br.odontoscheduler.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentService extends AbstractService<Appointment, AppointmentRepository> {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public AppointmentRepository getRepository() {
        return this.appointmentRepository;
    }

    public boolean updateAppointment(String id, Appointment appointment) {
        Optional<Appointment> optionalAppointment = this.appointmentRepository.findById(id);

        if (optionalAppointment.isPresent()) {
            Appointment currentAppointment = optionalAppointment.get();
            currentAppointment.setUpdatedAt(LocalDateTime.now());
            currentAppointment.setPatient(appointment.getPatient());
            currentAppointment.setDentistName(appointment.getDentistName());
            currentAppointment.setScheduledDate(appointment.getScheduledDate());
            this.appointmentRepository.save(currentAppointment);
            return true;
        }

        return false;
    }
}
