package com.br.odontoscheduler.service;

import com.br.odontoscheduler.model.Patient;

public interface PatientServiceInterface {
    public Patient save(Patient patient);
    public Patient getById(Patient patient);
}
