package com.example.composedemo.api

import com.example.composedemo.model.UserModel
import com.example.composedemo.utils.Globals
import retrofit2.http.GET

interface ApiService {

    @GET(Globals.BASE_URL)
    suspend fun getAllUsers(
    ): UserModel

}