package com.example.catlib.controller;

import com.example.catlib.facade.ServiceFacade;
import com.example.catlib.model.StoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ServiceFacade serviceFacade;

    public ContentController(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    @Operation(
            summary = "Save content by topic",
            description = "Fetches a cat image and book metadata for the given topic and stores them locally."
    )
    @PostMapping("/save/{topic}")
    public ResponseEntity<String> createContent(

            @Parameter(
                    description = "Topic used to fetch the cat image and book metadata",
                    example = "space"
            )
            @PathVariable String topic,

            @Parameter(
                    description = "Optional maximum number of books to fetch",
                    example = "10"
            )
            @RequestParam(required = false) Integer limit

    ) throws Exception {

        serviceFacade.createContent(topic, limit);

        return ResponseEntity.ok(
                "Content saved for topic: " + topic
        );
    }

    @Operation(
            summary = "Get all stored content",
            description = "Returns a summary of all topics currently stored locally."
    )
    @GetMapping("/summary")
    public ResponseEntity<List<StoreResponse>> getSummary()
            throws Exception {

        return ResponseEntity.ok(
                serviceFacade.getSummary()
        );
    }

    @Operation(
            summary = "Get stored content by topic",
            description = "Returns the stored summary for the given topic."
    )
    @GetMapping("/summary/{topic}")
    public ResponseEntity<StoreResponse> getSummaryByTopic(

            @Parameter(
                    description = "Topic to retrieve from local storage",
                    example = "space"
            )
            @PathVariable String topic

    ) throws Exception {

        return ResponseEntity.ok(
                serviceFacade.getSummaryByTopic(topic)
        );
    }
}