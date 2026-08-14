# CatLib

CatLib is a small Spring Boot REST API that fetches cat images from [CATAAS](https://cataas.com)
and book metadata from the [Open Library API](https://openlibrary.org/developers/api) by topic,
stores them locally, and lets you browse what has been stored.

## Requirements

- Java 17+
- Maven 3.8+ (or use the bundled `./mvnw` wrapper — no local Maven install needed)

## Running the app

```bash
./mvnw spring-boot:run
```

Or build and run the jar:

```bash
./mvnw clean package
java -jar target/catlib-0.0.1-SNAPSHOT.jar
```

The app starts on port `8080` by default.

## API documentation (Swagger)

Once running, the interactive API docs are available at:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Raw OpenAPI spec: http://localhost:8080/v3/api-docs

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/cat/{tag}` | Fetches a cat image URL from CATAAS for the given tag |
| POST | `/api/content/save/{topic}?limit={n}` | Fetches a cat image (CATAAS) and book metadata (Open Library) for the topic, and saves both locally. `limit` is optional and caps how many books are fetched |
| GET | `/api/content/summary` | Returns a summary of every topic currently stored locally |
| GET | `/api/content/summary/{topic}` | Returns the stored summary for a single topic (404 if not stored) |

**Example — save content for a topic:**
```
POST http://localhost:8080/api/content/save/space?limit=10
```
```json
"Content saved for topic: space"
```

**Example — get everything stored:**
```
GET http://localhost:8080/api/content/summary
```
```json
[
  {
    "topic": "space",
    "totalBooks": 10,
    "bookTitles": ["Revelation Space", "2001", "..."],
    "publishYearRange": { "from": 1899, "to": 2023 },
    "catImageUrl": "https://cataas.com/cat/5w3BlQo4wqJTLDJk",
    "openLibraryUrl": "https://openlibrary.org/search.json?q=space"
  }
]
```

## Where content is stored

Each `POST /api/content/save/{topic}` call creates a folder under `storage/{topic}/` containing:

```
storage/
  space/
    5w3BlQo4wqJTLDJk.jpg   # the cat image
    metadata.json          # book metadata from Open Library
```

Topic names are validated and normalized before being used as a folder path
(see `StoragePathUtil`), so path traversal input (e.g. `../../etc`) is rejected.

## Configuration

You can override defaults (like `server.port`) in `src/main/resources/application.properties`
or via environment variables.

## Notes

- The CATAAS API is free and requires no authentication: https://cataas.com
- The Open Library API is free and requires no authentication: https://openlibrary.org/developers/api
- Errors (invalid/missing tag, cat/book/topic not found, upstream failures) are returned as
  consistent JSON error bodies via a global exception handler, not stack traces.
