package com.bootcamp.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document()
public class Client {

    @Id
    String id;

    // DNI OR RUC
    private String identificaiton;

    // NAME OF CLIENTE
    private String name;


    private LocalDateTime createdAt;


    private LocalDateTime modifiedAt;


//      0: INACTIVE
//      1: ACTIVE

    private int state;

    public Client() {
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
        state = 1;
    }
}
