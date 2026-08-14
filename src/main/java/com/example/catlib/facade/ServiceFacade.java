package com.example.catlib.facade;

import com.example.catlib.model.BookResponse;
import com.example.catlib.model.CatResponse;
import com.example.catlib.model.StoreResponse;
import com.example.catlib.service.BookService;
import com.example.catlib.service.CatService;
import com.example.catlib.service.StorageService;
import com.example.catlib.service.SummaryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceFacade {

    private final CatService catService;
    private final BookService bookService;
    private final StorageService storageService;
    private final SummaryService summaryService;

    public ServiceFacade(
            CatService catService,
            BookService bookService,
            StorageService storageService,
            SummaryService summaryService
    ) {
        this.catService = catService;
        this.bookService = bookService;
        this.storageService = storageService;
        this.summaryService = summaryService;
    }

    public void createContent(
            String topic,
            Integer limit
    ) throws Exception {

        CatResponse catResponse =
                catService.fetchCatByTag(topic);

        BookResponse bookResponse =
                bookService.fetchBookByTopic(topic, limit);

        storageService.save(
                topic,
                catResponse,
                bookResponse
        );
    }

    public List<StoreResponse> getSummary() throws Exception {
        return summaryService.getSummary();
    }
    public StoreResponse getSummaryByTopic(String topic) throws Exception {
        return summaryService.getSummaryByTopic(topic);
    }
}