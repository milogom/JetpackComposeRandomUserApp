# Jetpack Compose MVVM Random User App.
Miguel Angel Lozano Gomez.

This Android Application is built using Jetpack Compose and MVVM Architecture, and accesses the [Random User Generator Api](https://randomuser.me/documentation) for fetching data, showing user location in Google Maps (based on random coordinates, so don't expect address and gps location match)

https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/1679c4dc-eac9-4ce2-a0e5-a517873acd71


# Built With
+ Jetpack Compose for UI
+ MVVM Architecture
+ Modern Splash Screen
+ Retrofit (for API Calling).
+ Duplicated registers control.
+ Hilt Dependency Injection.
+ Coil (for Image Loading).
+ Kotlin Coroutines.
+ Sealed Class.
+ Google Maps API.
+ Infinite scroll (not paged, and limited to 500 radom users).
+ Screens navigation.
+ String language translations for labels, and database strings responses (Eg. gender = male).
+ Customized Fonts and Styles.



+ App Customized Launch Icon.
+ Utils and Globals classes for converting dates, storing constants, database strings response translation, etc.

# Notes
+ RandomUser.me database doesn't have and endpoint for fetching all users, you need to use a limit (EG. "[https://](https://randomuser.me/api/?results=500)https://randomuser.me/api/?results=500").
+ This app limits the random users created to 500.
+ Future implementation with Room, to locally store users, edit, add, and delete users.

# Figma Design Requirements
<img width="655" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/a4fc4367-4801-47bd-9907-e6fe08184ff2">

# Results
<img width="300" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/4abc7d24-2cd7-4c0c-9100-a8acdce66e3f">
<img width="300" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/df01e6ce-32a6-47b5-85f5-1286c7148e43">








