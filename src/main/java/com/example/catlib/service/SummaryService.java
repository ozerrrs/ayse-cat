package com.example.catlib.service;

import com.example.catlib.exception.TopicNotFoundException;
import com.example.catlib.model.Book;
import com.example.catlib.model.BookResponse;
import com.example.catlib.model.PublishYearRange;
import com.example.catlib.model.StoreResponse;
import com.example.catlib.util.StoragePathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SummaryService {


    private final ObjectMapper objectMapper;

    public SummaryService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


   public List<StoreResponse> getSummary() throws Exception {

       Path storagePath = StoragePathUtil.getStorageDirectory();

       List<StoreResponse> summaries = new ArrayList<>();

       if (!Files.exists(storagePath)) {
           return summaries;
       }

       try (Stream<Path> directories = Files.list(storagePath)) {

           for (Path topicDirectory : directories
                   .filter(Files::isDirectory)
                   .toList()) {

               Path metadataPath =
                       topicDirectory.resolve("metadata.json");

               if (!Files.exists(metadataPath)) {
                   continue;
               }

               StoreResponse response =
                       createStoreResponse(topicDirectory);

               summaries.add(response);
           }
       }

       return summaries;
   }
    public StoreResponse getSummaryByTopic(String topic) throws Exception {

        Path topicDirectory =
                StoragePathUtil.getTopicDirectory(topic);

        Path metadataPath =
                topicDirectory.resolve("metadata.json");

        if (!Files.exists(metadataPath)) {
            throw new TopicNotFoundException(topic);
        }

        return createStoreResponse(topicDirectory);
    }
    private StoreResponse createStoreResponse(
            Path topicDirectory
    ) throws Exception {

        Path metadataPath =
                topicDirectory.resolve("metadata.json");

        BookResponse bookResponse =
                objectMapper.readValue(
                        metadataPath.toFile(),
                        BookResponse.class
                );

        List<String> titles = bookResponse
                .getBooks()
                .stream()
                .map(Book::getTitle)
                .toList();

        List<Integer> years = bookResponse
                .getBooks()
                .stream()
                .map(Book::getFirstPublishYear)
                .filter(year -> year != null)
                .toList();

        Integer from = years.stream()
                .min(Integer::compareTo)
                .orElse(null);

        Integer to = years.stream()
                .max(Integer::compareTo)
                .orElse(null);

        String catImageUrl =
                getCatImageUrl(topicDirectory);

        return new StoreResponse(
                bookResponse.getTopic(),
                bookResponse.getBooks().size(),
                titles,
                new PublishYearRange(from, to),
                catImageUrl,
                bookResponse.getOpenLibraryUrl()
        );
    }
    private String getCatImageUrl(Path topicDirectory) throws Exception {

        try (Stream<Path> files = Files.list(topicDirectory)) {

            Path imagePath = files
                    .filter(path -> path.toString().endsWith(".jpg"))
                    .findFirst()
                    .orElse(null);

            if (imagePath == null) {
                return null;
            }

            String fileName = imagePath
                    .getFileName()
                    .toString();

            String catId = fileName.replace(".jpg", "");

            return "https://cataas.com/cat/" + catId;
        }
    }
}