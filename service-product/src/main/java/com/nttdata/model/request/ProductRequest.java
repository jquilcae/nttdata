package com.nttdata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductRequest {


    private String customerId;

//    1: person
//    2: businnes

    private int customerType;

    /**
     * 1: saving
     * 2: Checking account
     * 3: fix term
     * <p>
     * credit target
     * <p>
     * 4: Personal
     * 5: Business
     * 6: Personal credit target o business
     */
    private int accountType;


    private BigDecimal amount;

    private String cardNumber;


//      1: passive
//      2: active


    private int productType;
}
