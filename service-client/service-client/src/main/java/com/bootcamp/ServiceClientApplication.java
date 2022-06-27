package com.bootcamp;

import com.bootcamp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceClientApplication {

    @Autowired
    PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceClientApplication.class, args);
    }


}
