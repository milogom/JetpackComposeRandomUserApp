package com.example.composedemo.navigation

// Define una clase sellada (sealed class) para representar las pantallas de la aplicación.
// Usar una clase sellada permite agrupar un conjunto limitado de tipos y es útil para representar
// estados o tipos fijos, como las pantallas en la navegación.
sealed class AppScreens(val route: String) {

    // Define un objeto para representar la pantalla de inicio.
    // Este objeto hereda de AppScreens y tiene una ruta única asociada.
    // "home_screen" es el identificador de ruta para la pantalla de inicio.
    object Home : AppScreens(route = "home_screen")

    // Define otro objeto para representar la pantalla de detalles.
    // Al igual que Home, hereda de AppScreens y tiene su propia ruta única.
    // "details_screen" es el identificador de ruta para la pantalla de detalles.
    object Details : AppScreens(route = "details_screen")
}
