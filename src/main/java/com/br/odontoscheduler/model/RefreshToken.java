package com.br.odontoscheduler.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Document(collection = "refresh_token")
public class RefreshToken {

    @Id
    private String id;

    @DocumentReference(lazy = true)
    private User owner;
}
