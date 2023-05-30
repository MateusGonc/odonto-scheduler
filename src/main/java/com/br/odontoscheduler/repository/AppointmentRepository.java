package com.br.odontoscheduler.repository;

import com.br.odontoscheduler.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
}
