package com.example.catlib.service;

import com.example.catlib.exception.BookNotFoundException;
import com.example.catlib.model.Book;
import com.example.catlib.model.BookResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private static final String OPEN_LIBRARY_BASE_URL = "https://openlibrary.org";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private HttpResponse<String> sendRequest(HttpRequest request)
            throws Exception {

        try {
            return httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

        } catch (javax.net.ssl.SSLHandshakeException e) {

            return httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
        }
    }
    private final ObjectMapper objectMapper;

    public BookService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public BookResponse fetchBookByTopic(
            String topic,
            Integer limit
    ) throws Exception {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(OPEN_LIBRARY_BASE_URL)
                .path("/search.json")
                .queryParam("q", topic);

        if (limit != null) {
            uriBuilder.queryParam("limit", limit);
        }

        URI uri = uriBuilder
                .build()
                .encode()
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = sendRequest(request);

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException();
        }

        JsonNode root = objectMapper.readTree(response.body());

        JsonNode docs = root.path("docs");

        if (!docs.isArray() || docs.isEmpty()) {
            throw new BookNotFoundException(topic);
        }

        List<Book> books = new ArrayList<>();

        for (JsonNode bookNode : docs) {

            String title = bookNode
                    .path("title")
                    .asText();

            String author = "";

            JsonNode authors = bookNode.path("author_name");

            if (authors.isArray() && !authors.isEmpty()) {
                author = authors.get(0).asText();
            }

            Integer firstPublishYear = null;

            if (bookNode.has("first_publish_year")) {
                firstPublishYear = bookNode
                        .path("first_publish_year")
                        .asInt();
            }

            String openLibraryUrl = "";

            if (bookNode.has("key")) {
                openLibraryUrl =
                        OPEN_LIBRARY_BASE_URL
                                + bookNode.path("key").asText();
            }

            Book book = new Book(
                    title,
                    author,
                    firstPublishYear,
                    openLibraryUrl
            );

            books.add(book);
        }

        return new BookResponse(
                topic,
                uri.toString(),
                books
        );
    }


}