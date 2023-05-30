package com.br.odontoscheduler.model;

import com.br.odontoscheduler.dto.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document(collection = "patient")
public class Patient extends BaseEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String document;

    @Indexed(unique = true)
    private String phoneNumber;

    @Indexed(unique = true)
    private String email;

    private String name;
    private int age;
    private Address address;
}
