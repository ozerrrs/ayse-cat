package com.example.catlib.service;

import com.example.catlib.model.BookResponse;
import com.example.catlib.model.CatResponse;
import com.example.catlib.util.StoragePathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class StorageService {


    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public StorageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void save(
            String topic,
            CatResponse catResponse,
            BookResponse bookResponse
    ) throws Exception {

        Path topicDirectory = StoragePathUtil.getTopicDirectory(topic);

        Files.createDirectories(topicDirectory);

        saveImage(topicDirectory, catResponse.getImageUrl());

        saveMetadata(topicDirectory, bookResponse);
    }

    private void saveImage(Path topicDirectory, String imageUrl) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageUrl))
                .GET()
                .build();

        HttpResponse<byte[]> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofByteArray()
        );

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException(
                    "Image download failed. Status: " + response.statusCode()
            );
        }

        try (Stream<Path> files = Files.list(topicDirectory)) {
            for (Path file : files
                    .filter(path -> path.toString().endsWith(".jpg"))
                    .toList()) {

                Files.delete(file);
            }
        }

        String catId = imageUrl.substring(
                imageUrl.lastIndexOf("/") + 1
        );

        Path imagePath =
                topicDirectory.resolve(catId + ".jpg");

        Files.write(imagePath, response.body());
    }
    private void saveMetadata(
            Path topicDirectory,
            BookResponse bookResponse
    ) throws Exception {

        Path metadataPath = topicDirectory.resolve("metadata.json");

        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(metadataPath.toFile(), bookResponse);
    }
}