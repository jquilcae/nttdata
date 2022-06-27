package com.nttdata.dao.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Detail {



    private BigDecimal amount;


    private String details;

    /**
     * 1: consume
     * 2: deposits
     * 3: withdrawals
     * */
    private int transactionType;

    /**
     * 1: saving
     * 2: Checking account
     * 3: fix term
     *
     * 4: Personal
     * 5: Business
     * 6: personal credit target or business
     * */
    private int accountType;

//   Number of account
    private String cardNumber;


    private LocalDateTime createdAt;

    public Detail(){
        createdAt=LocalDateTime.now();
    }



}
