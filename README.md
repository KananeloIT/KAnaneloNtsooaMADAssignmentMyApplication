<<<<<<< HEAD
Pantry Management System  
=======
# Smart Pantry

Smart Pantry is a Java Android app for keeping a local list of kitchen ingredients, editing or removing pantry entries, and browsing simple recipe ideas. It includes Pantry, Add/Edit Ingredient, Suggested Recipes, Recipe Detail, and Settings screens.

## Database

The app uses SQLite through Android's `SQLiteOpenHelper`. Pantry items are stored on the device and remain available after the app closes. SQLite is a good fit for this assignment because the data is local, structured, and does not require a network connection or a separate backend.

## Requirements covered

- Java implementation for Android Studio.
- Five Activities connected with explicit Android Intents.
- SQLite Create, Read, Update, and Delete operations for pantry items.
- Custom `PantryAdapter` in a `ListView` for pantry data.
- Bottom navigation buttons for Pantry, Recipes, and Settings.
- Ingredient form validation, including a positive numeric quantity.
- Recipe suggestions and a recipe detail screen.
- Settings preference saved with `SharedPreferences`.

## Setup and run

1. Open this project in Android Studio.
2. Allow Gradle to sync and install the Android SDK platform configured by the project.
3. Select an emulator or Android device (Android 7.0 / API 24 or later).
4. Run the `app` configuration.
5. Use **Add ingredient** to create pantry entries. Tap an entry to edit it, or use its **Delete** button to remove it.

Pantry data is held in the app's local database (`smartpantry.db`). Uninstalling the app or clearing its storage removes that local data.
>>>>>>> e0fdc1f (Add project README documentation)
