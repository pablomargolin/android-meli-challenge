# Space Flight News - Mercado Libre Challenge

A modular Android application that consumes the Space Flight News API to display the latest articles about space flights. This project was developed as a technical challenge for Mercado Libre, focusing on **Clean Architecture**, **MVVM**, **Modularization**, and **Robust Error Handling**.

## 🚀 Features

- **Real-time Search:** Search for articles with a debounced search bar (500ms delay) to optimize API calls.
- **Infinite Pagination:** Smooth scrolling with automatic loading of next pages and duplicate article prevention.
- **Resilient Data Layer:** Gracefully handles malformed API responses (e.g., missing IDs) without crashing.
- **Domain Integrity:** Strict domain models with non-nullable critical fields, ensuring UI stability.
- **Design System:** Custom UI components built with Jetpack Compose using a modular and themeable approach.
- **Error Handling:** Comprehensive error states (No Internet, Server Error, Not Found) with retry functionality.

## 🏗️ Architecture

The project follows **Clean Architecture** principles and is divided into several modules to ensure separation of concerns and scalability:

### 📦 Module Structure
- `:app`: Entry point, Navigation (NavGraph), and Hilt configuration.
- `:feature:news-feed`: List of articles, Search, and Pagination logic.
- `:feature:news-detail`: Full article details and external link handling.
- `:core:domain`: Pure Kotlin module containing Domain Models, Use Cases, and Repository interfaces.
- `:core:network`: Retrofit setup, DTOs, Mappers, and centralized API error handling.
- `:core:shared-ui`: Design System, Themes, and reusable Compose components.

### 🛠️ Tech Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose
- **DI:** Hilt
- **Asynchrony:** Coroutines & Flow
- **Network:** Retrofit & OkHttp
- **JSON Parsing:** Moshi
- **Image Loading:** Coil
- **Logging:** Timber
- **Testing:** JUnit, MockK, Turbine (for Flow testing), and Compose UI Tests.

## 🧪 Quality & Testing

The project maintains high standards through:
- **Unit Tests:** Extensive coverage for ViewModels, Use Cases, Mappers, and Repositories.
- **Resilience Tests:** Verified behavior for debounce, deduplication, and API contract failures.
- **UI Tests:** Automated tests for loading, error, and success states in both main features.
- **Clean Code:** Adherence to SOLID principles and DRY patterns.

## ⚙️ How to Run

1. Clone this repository.
2. Open the project in **Android Studio Ladybug** (or newer).
3. Wait for Gradle sync to complete.
4. Run the `app` module on an emulator or physical device with **API 29+**.

---
*Developed by Pablo Margo as part of the Mercado Libre Mobile Candidate Selection Process.*
