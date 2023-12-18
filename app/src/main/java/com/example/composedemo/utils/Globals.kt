package com.example.composedemo.utils

object Globals {

    // La base de datos de randomuser no tiene un listado limitado de user,
    // se generan automáticamente y de forma infinita, por lo tanto si queremos una función
    // de respuesta tipo getAllUsers() necesitamos limitar la generación automática
    const val BASE_URL: String = "https://randomuser.me/api/?results=500"
}