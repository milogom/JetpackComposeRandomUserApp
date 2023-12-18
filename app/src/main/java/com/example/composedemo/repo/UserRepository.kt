package com.example.composedemo.repo

// Importaciones necesarias.
import com.example.composedemo.api.ApiService
import javax.inject.Inject

// Definición de la clase UserRepository.
// La anotación @Inject indica que esta clase es elegible para la inyección de dependencias.
// Esto significa que se pueden inyectar automáticamente sus dependencias cuando se crea una instancia de esta clase.
class UserRepository @Inject constructor(private var apiService: ApiService) {

    // Función suspendida que envuelve la llamada a la función getAllUsers() del ApiService.
    // Esta función es suspendida, lo que significa que puede ser pausada y reanudada sin bloquear el hilo en el que se ejecuta.
    // Es útil para operaciones que pueden tomar tiempo, como llamadas a una API de red.
    suspend fun getAllUser() = apiService.getAllUsers()
}
