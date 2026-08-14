package com.example.catlib.service;

import com.example.catlib.exception.CatNotFoundException;
import com.example.catlib.exception.InvalidTagException;
import com.example.catlib.model.CatResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CatService {
    private static final String CATAAS_BASE_URL = "https://cataas.com";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public CatService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CatResponse fetchCatByTag(String tag) throws Exception {
            if (tag == null || tag.isBlank()) {
                throw new InvalidTagException();
            }

            String url = UriComponentsBuilder
                    .fromUriString(CATAAS_BASE_URL)
                    .pathSegment("cat", tag)
                    .queryParam("json", true)
                    .build()
                    .encode()
                    .toUriString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 404) {
                throw new CatNotFoundException(tag);
            }

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException();
            }

            JsonNode root = objectMapper.readTree(response.body());

            String catId = root.path("id").asText();

            if (catId.isBlank()) {
                throw new CatNotFoundException(tag);
            }

            String imageUrl = CATAAS_BASE_URL + "/cat/" + catId;

            return new CatResponse(tag, imageUrl);
        }
}
