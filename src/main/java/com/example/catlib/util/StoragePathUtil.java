package com.example.catlib.util;

import java.nio.file.Path;

public class StoragePathUtil {

    private static final Path STORAGE_ROOT =
            Path.of("storage").toAbsolutePath().normalize();

    public static Path getStorageDirectory() {
        return STORAGE_ROOT;
    }

    public static Path getTopicDirectory(String topic) {

        if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("Topic is required.");
        }

        Path topicPath = STORAGE_ROOT
                .resolve(topic)
                .normalize();

        if (!topicPath.startsWith(STORAGE_ROOT)) {
            throw new IllegalArgumentException("Invalid topic.");
        }

        return topicPath;
    }
}