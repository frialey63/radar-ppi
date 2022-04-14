package org.pjp.radar.rest;

import org.pjp.radar.rest.dto.Aircraft;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

public class RestClient {

    private static final String REST_URI = "http://localhost:8080/dump1090processor/aircraft";

    private Client client = ClientBuilder.newClient();

    public Aircraft[] getAllAircraft() {
        return client.target(REST_URI).path("all").request(MediaType.APPLICATION_JSON).get(Aircraft[].class);
    }

}
