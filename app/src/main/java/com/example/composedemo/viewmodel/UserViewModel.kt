package com.example.composedemo.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.model.User
import com.example.composedemo.model.UserModel
import com.example.composedemo.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    val isLoading = mutableStateOf(false)
    private var _userData = MutableStateFlow<List<UserModel>>(emptyList())
    val userData = _userData.asStateFlow()

    // Usuario seleccionado para la pantalla de detalles
    var user by mutableStateOf<User?>(null)
        private set

    fun getAllUser() = viewModelScope.launch {
        isLoading.value = true
        val fetchUserListEntity = repository.getAllUser()

        // Filtra los usuarios para eliminar duplicados basados en el email
        val uniqueUsers = fetchUserListEntity.results.distinctBy { it.email }

        // Actualiza _userData con los usuarios únicos
        _userData.value = listOf(UserModel(ArrayList(uniqueUsers), fetchUserListEntity.info))
    }.invokeOnCompletion {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            isLoading.value = false
        }, 3000)
    }

    fun addUser(newUser: User) {
        user = newUser
    }

}