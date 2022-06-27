package com.nttdata.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String clientId;

//      1: passive
//      2: active

    private String productType;


//      1:person
//      2:business

    private String customerType;

    private List<CustomerResponse> customerResponses = new ArrayList<>();
    private List<AccountResponse> accountResponses = new ArrayList<>();
}
