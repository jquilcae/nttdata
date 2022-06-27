package com.nttdata.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nttdata.client.ProductResultDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = ProductResultDeserializer.class)
public class AccountRequest {
    /**
     * 1: SAVING
     * 2: Checking ACCOUNT
     * 3: FIXED TERM


     * TC
     * 4: Personal
     * 5: BUSINESS
     * 6: Personal credit target or business
     */
    private String accountType;

    private String cardNumber;
    private BigDecimal amount;
}
