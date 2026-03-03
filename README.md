# Space Flight News - Mercado Libre Challenge

A modular Android application that consumes the Space Flight News API to display the latest articles about space flights. This project was developed as a technical challenge for Mercado Libre.

## 🚀 Features

- **Real-time Search:** Search for articles with a debounced search bar to optimize API calls.
- **Infinite Pagination:** Smooth scrolling with automatic loading of next pages and duplicate article prevention.
- **Resilient Data Layer:** Handles malformed API responses (e.g., missing IDs) without crashing.
- **Domain Integrity:** Strict domain models with non-nullable critical fields, ensuring UI stability.
- **Design System:** Custom UI components built with Jetpack Compose using a modular and themeable approach.
- **Error Handling:** Error states with retry functionality.

## 🏗️ Architecture

The project follows **Clean Architecture** principles and is divided into several modules to ensure separation of concerns and scalability:

### 📦 Module Structure
- `:app`: Entry point, Navigation and Hilt configuration.
- `:feature:news-feed`: List of articles, Search, and Pagination logic.
- `:feature:news-detail`: Full article details and external link handling.
- `:core:domain`: Pure Kotlin module containing Domain Models, Result and utils extensions.
- `:core:network`: Retrofit setup, DTOs, Mappers, and centralized API error handling.
- `:core:shared-ui`: Design System, Themes, and reusable Compose components.

### 🛠️ Tech Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose
- **DI:** Hilt
- **Network:** Retrofit & OkHttp
- **JSON Parsing:** Moshi
- **Image Loading:** Coil
- **Logging:** Timber
- **Testing:** JUnit, MockK, Turbine, and Compose UI Tests.

---
*Developed by Pablo Margolin as part of the Mercado Libre Mobile Candidate Selection Process.*

*[Documentacion Técnica](https://docs.google.com/document/d/1hzhmX9AW6UySbJQul4CqBDbOn1_qHPcDLznS3-_UGWs/edit?usp=sharing)*

*[APK Debug](https://drive.google.com/file/d/1aDdyGTOWqiWNtwD8G0hKu9Lj9qwDzuM4/view?usp=drive_link)*