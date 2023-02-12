package com.br.odontoscheduler.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "patients")
public class Patient {

    @Id
    private String id;

    private String name;

    private String document;
    private String phoneNumber;
    private String email;
    private int age;
}
