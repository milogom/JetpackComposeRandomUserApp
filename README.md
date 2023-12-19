# Jetpack Compose MVVM Random User App.
Miguel Angel Lozano Gomez.

This Android Application is built using Jetpack Compose and MVVM Architecture, and accesses the [Random User Generator Api](https://randomuser.me/documentation) for fetching data, showing user location in Google Maps (based on random coordinates, so don't expect address and gps location match)

https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/c35796df-1a87-4a0f-b316-47e2cfe3f4e6

# Built With
+ Jetpack Compose for UI.
+ MVVM Architecture.
+ Clean code.
+ Modern custom logo splash screen and app icon. 
+ Retrofit (for API Calling).
+ Duplicated registers control.
+ Functional real time search engine with clean function when close
+ Hilt dependency injection.
+ Coil (for image loading and caching).
+ Kotlin coroutines.
+ Sealed class.
+ Google Maps API.
+ Infinite scroll (not paged, and limited to 500 radom users).
+ Screens navigation.
+ String language translations for labels, and database strings responses (Eg. gender = male).
+ Customized fonts and styles.
+ Customized "Compostables" folder with reusable compostables.
+ Utils and Globals classes for converting dates, storing constants, database strings response translation, etc.

# Notes
+ RandomUser.me database doesn't have and endpoint for fetching all users, you need to use a limit (EG. "[https://](https://randomuser.me/api/?results=500)https://randomuser.me/api/?results=500").
+ This app limits the random users created to 500.
+ Future implementation with Room, to locally store users, edit, add, and delete users.

# Figma Design Requirements
<img width="655" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/a4fc4367-4801-47bd-9907-e6fe08184ff2">

# Results
<img width="350" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/72179661-342a-49f5-8055-2f4e358a9b45">
<img width="350" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/6b886cc5-b419-487c-bac7-af16f059c782">
<img width="350" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/425da3c2-9182-4ec3-96d5-b38ff35b2ede">
<img width="350" alt="image" src="https://github.com/milogom/JetpackComposeRandomUserApp/assets/6615507/af06d9ba-af71-448a-bf99-cac8de1b3e3e">



# Pending
+ Database unit test







