package com.nttdata.client.dto;


import com.nttdata.client.ClientResultDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = ClientResultDeserializer.class)
public class ClientResult {

    String clientId;
    String name;
    String identification;


}
