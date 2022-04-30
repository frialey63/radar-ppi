package org.pjp.radar.rest;

import org.pjp.radar.rest.dto.Aircraft;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

public class RestClient {

    private static final String DUMP1090_URI = "http://localhost:8080/dump1090processor/aircraft";

    private static final String OPENSKY_URI = "http://localhost:8082/opensky/aircraft";

    private Client client = ClientBuilder.newClient();

    public Aircraft[] getAllAircraft() {
        return client.target(DUMP1090_URI).path("all").request(MediaType.APPLICATION_JSON).get(Aircraft[].class);
    }

    public String getCategoryByIcao24(String icao24) {
        return client.target(OPENSKY_URI).path(icao24).path("category").request(MediaType.APPLICATION_JSON).get(String.class);
    }
}
