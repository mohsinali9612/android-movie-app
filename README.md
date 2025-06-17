# Movie App

## Project Overview

Movie App is a modern Android application built with Jetpack Compose, Kotlin, and a clean MVI architecture. The app demonstrates advanced UI/UX patterns, efficient state management, and robust networking using Ktor. It allows users to search for movies and TV shows, view details, and play videos, all with a smooth and responsive interface.

### Key Features
- **Movie/TV Search:** Search for movies and TV shows using The Movie Database (TMDB) API.
- **Paginated Results:** Manual pagination handle because list is being displayed horizontally.
- **Detail Screen:** View detailed information about each media item.
- **Video Playback:** Play trailers or videos using ExoPlayer.
- **Modern UI:** Built with Jetpack Compose, Material3, and custom theming.
- **Navigation:** Type-safe navigation using Compose Destinations and custom serializers.
- **Koin DI:** Dependency injection with Koin for scalable architecture.
- **Error Handling:** Robust error and network state handling.

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/mohsinali9612/android-movie-app.git
cd project_path
```

### 2. Minimum Requirements
- **Android Studio:** Giraffe (2022.3.1) or newer
- **Kotlin:** 2.1.21 or newer
- **Android SDK:** Compile/Target SDK 35, Min SDK 24
- **RAM:** 8GB+ recommended
- **JDK:** Java 17

### 3. Setting Up API Keys

This project uses The Movie Database (TMDB) API. You must provide your own API key and access token.

#### Steps:
1. In the project root, create or edit the file named `local.properties` (this file is not tracked by git).
2. Add the following lines, replacing the values with your own TMDB credentials:

```properties
TMDB_API_KEY=your_tmdb_api_key_here
TMDB_ACCESS_TOKEN=your_tmdb_access_token_here
```

> **⚠️ WARNING:**
> You must use the exact variable names: `TMDB_API_KEY` and `TMDB_ACCESS_TOKEN` (case-sensitive) in your `local.properties` file. Otherwise, the app will not be able to authenticate with the TMDB API.

### 4. Build and Run
- Open the project in Android Studio.
- Let Gradle sync and download dependencies.
- Connect an Android device or start an emulator (API 24+).
- Click **Run** (▶️) or use `Shift+F10`.

## Project Structure

```
Movie App/
  ├── app/                  # Main Android app module
  │   ├── src/main/java/com/example/movie_appp/
  │   │   ├── presentation/ # UI, screens, navigation
  │   │   ├── di/           # Dependency injection setup
  │   │   └── MainActivity.kt
  │   └── res/              # Resources (layouts, drawables, etc.)
  ├── core-data/            # Data layer (network, models, repository)
  ├── utils-extension/      # Shared utilities and extensions
  ├── build.gradle.kts      # Project-level Gradle config
  ├── settings.gradle.kts   # Gradle settings
  └── local.properties      # (Not tracked) Your API keys
```

## Main Technologies Used
- **Jetpack Compose** (UI)
- **Kotlin** (Language)
- **Ktor** (Networking)
- **Koin** (Dependency Injection)
- **Coil** (Image Loading)
- **ExoPlayer** (Video Playback)
- **Material3** (Design System)

## Notes
- The app is set to portrait mode by default, but the video player enforces landscape mode for playback.
- All API/network errors are handled gracefully with user feedback.
- The project is modular for easy extension and maintenance.

## License
This project is for educational and demonstration purposes. Please check TMDB's API terms of use for any production deployment. 