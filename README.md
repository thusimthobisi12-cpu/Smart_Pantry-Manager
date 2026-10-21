Smart Pantry Manager

A Java Android app that helps reduce food waste. The user adds the ingredients they have at home, and the application suggests only the recipes they can cook using those ingredients. No shopping trip needed.

Built for Mobile App Development 700.

Student name and surname: Mthobisi Thusi
Student number: 402411764

 Features

- Add, edit and delete pantry items (name, quantity, unit and an optional expiry date)
- Pantry list screen using a RecyclerView with a custom adapter
- 20 starter recipes, loaded into the database the first time the app runs
- Suggested Recipes screen that uses strict matching
- Recipe Detail screen with the full ingredient list and method
- Settings screen with an "expiring-soon alerts" switch, saved on the phone
- Bottom navigation bar (Pantry, Recipes, Settings)
- Input validation on the add/edit form
- A friendly message when no recipes match

The strict-matching rule

A recipe is only suggested if EVERY ingredient it needs is in the pantry, in at least the required quantity. If a recipe needs 5 ingredients and the pantry has 4, it is not shown.

The matching is also tolerant of simple differences:
- Units are converted (1 kg equals 1000 g, 1 l equals 1000 ml)
- Names are made lowercase and singular ("Tomatoes" matches "tomato")
- Several pantry rows of the same ingredient are added together

Database: Room (SQLite)

I chose Room (SQLite) because:
- The data is stored on the phone, so the app works without internet
- The data stays after the app is closed and reopened
- It supports full CRUD (create, read, update, delete)
- It needs no backend server, so it is simple and reliable

Tables:
- pantry_items (id, name, quantity, unit, expiryDate)
- recipes (id, name, steps)
- recipe_ingredients (id, recipeId, name, quantity, unit)

Technologies

- Java 
- Android Studio
- RecyclerView, Intents, SharedPreferences, Material BottomNavigationView



1. Install Android Studio.
2. Clone this repository, or download it as a ZIP and unzip it.
3. Open the project in Android Studio (File, then Open, then choose the project folder).
4. Wait for Gradle to finish syncing.
5. Create an emulator (Device Manager) or connect an Android phone with USB debugging turned on.
6. Click the green Run button.

The app does not use Google Maps, GPS or any location features.