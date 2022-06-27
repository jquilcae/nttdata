package com.nttdata.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class Account {


  @Id
  private String id;
  private String productId;

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
    private int accountType;

    private String cardNumber;
    private BigDecimal amount;
}
