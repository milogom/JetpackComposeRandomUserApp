# Jetpack Compose MVVM Random User App.
Miguel Angel Lozano Gomez.

This Android Application is built using Jetpack Compose and MVVM Architecture, and accesses the [Random User Generator Api](https://randomuser.me/documentation) for fetching data, showing user location in Google Maps (based on random coordinates, so don't expect same address and gps location)

https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/1a22aadf-c720-413b-aa6f-990c1c586d53

# Built With
+ Jetpack Compose for UI
+ MVVM Architecture
+ Modern Splash Screen
+ Retrofit (for API Calling).
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

Results:

<img width="300" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/4abc7d24-2cd7-4c0c-9100-a8acdce66e3f">
<img width="300" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/268933b7-d679-4669-aebf-5ed372d5295d">







