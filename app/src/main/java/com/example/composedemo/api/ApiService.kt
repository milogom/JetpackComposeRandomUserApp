package com.example.composedemo.api

import com.example.composedemo.model.User
import com.example.composedemo.model.UserModel
import com.example.composedemo.utils.Globals
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Globals.BASE_URL + "?results=500")
    suspend fun getAllUsers(): UserModel

    // Testing purposes: Obtendremos un usuario para probar la conexión a la BBDD
    @GET(Globals.BASE_URL)
    suspend fun getUser(): Response<User>

}