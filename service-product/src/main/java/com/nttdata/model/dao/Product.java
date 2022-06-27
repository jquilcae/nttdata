package com.nttdata.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Product {

    @Id
    private String id;

    private String clientId;

//     1: passive
//     2: active

    private int productType;

    /**
     * 1: person
     * 2: business
     * */
    private int customerType;


}
