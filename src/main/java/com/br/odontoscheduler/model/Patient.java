package com.br.odontoscheduler.model;

import com.br.odontoscheduler.model.base.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@ToString
@Document(collection = "patient")
public class Patient extends BaseEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String fullName;

    @Indexed(unique = true)
    private String document;

    @Indexed(unique = true)
    private String phoneNumber;

    @Indexed(unique = true)
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;
    private Address address;
}
