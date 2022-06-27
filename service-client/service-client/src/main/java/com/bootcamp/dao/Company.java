package com.bootcamp.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends Client{

// RUC IDENTIFIER FOR BUSINESS
    private String ruc;
}
