package com.nttdata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    /**
     * 1: saving
     * 2: Checking account
     * 3: fix term
     *
     * credit target
     *
     * 4: Personal
     * 5: Business
     * 6: Personal credit target o business
     * */
    private String accountType;

    private String cardNumber;
    private BigDecimal amount;
}
