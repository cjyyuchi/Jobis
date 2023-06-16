package com.example.jobis.util;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Slf4j
public class JerseyClientUtil {

    public static String get(String uri, HashMap<String, Object> entity){

        Client client = ClientBuilder.newClient();

        Response response = client.target(uri)
//                .path("/v2/scrap")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE));

        if (response.getStatus() != 200) {
            log.warn("API 호출 실패 :: {}", response.getStatus());
        }

        String responseEntity = response.readEntity(String.class);

        return responseEntity;
    }









}
