package com.vanrides.vanrides.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class GeocodingService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Coordenadas buscarCoordenadas(String enderecoCompleto) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                    .queryParam("address", enderecoCompleto)
                    .queryParam("key", apiKey)
                    .toUriString();

            String resposta = restTemplate.getForObject(url, String.class);

            JSONObject json = new JSONObject(resposta);
            String status = json.getString("status");

            if (!status.equals("OK")) {
                throw new RuntimeException("Erro na resposta da API: " + status);
            }

            JSONArray results = json.getJSONArray("results");
            JSONObject location = results.getJSONObject(0)
                                         .getJSONObject("geometry")
                                         .getJSONObject("location");

            double latitude = location.getDouble("lat");
            double longitude = location.getDouble("lng");

            return new Coordenadas(latitude, longitude);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar coordenadas: " + e.getMessage(), e);
        }
    }

    public static class Coordenadas {
        private double latitude;
        private double longitude;

        public Coordenadas(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
