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
import com.example.composedemo.utils.getUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    val isLoading = mutableStateOf(false)
    private var _userData = MutableStateFlow<List<UserModel>>(emptyList())
    val userData = _userData.asStateFlow()

    // Valores para búsqueda de usuarios
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Valores para mostrar u ocultar el campo de búsqueda
    private val _isSearchVisible = MutableStateFlow(false)
    val isSearchVisible: StateFlow<Boolean> = _isSearchVisible.asStateFlow()

    // Combina _userData y _searchQuery para obtener una lista de usuarios filtrada
    val filteredUserData = combine(_userData, _searchQuery) { userModels, query ->
        userModels.map { userModel ->
            // Filtra cada lista de User dentro de UserModel
            UserModel(
                results = userModel.results.filter { user ->
                    user.name.getUsername().contains(query, ignoreCase = true) ||
                            user.email.contains(query, ignoreCase = true)
                } as ArrayList<User>,
                info = userModel.info
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())



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

    fun toggleSearchVisibility() {
        _isSearchVisible.value = !_isSearchVisible.value
        // Si el campo de búsqueda se está ocultando, limpia la consulta de búsqueda
        if (!_isSearchVisible.value) {
            _searchQuery.value = ""
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Filtrado de usuarios del motor de búsqueda.
    private fun filterUsers(users: List<User>, query: String): List<User> {
        // Filtra la lista basada en la consulta de búsqueda
        return if (query.isEmpty()) {
            users
        } else {
            users.filter {
                it.name.getUsername().contains(query, ignoreCase = true) ||
                        it.email.contains(query, ignoreCase = true)
            }
        }
    }
}