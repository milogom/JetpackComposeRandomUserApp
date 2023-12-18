package com.example.composedemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    // LiveData para almacenar el título de la TopAppBar
    private val _topBarTitle = MutableLiveData<String>()
    val topBarTitle: LiveData<String> = _topBarTitle

    // Función para actualizar el título
    fun setTopBarTitle(newTitle: String) {
        _topBarTitle.value = newTitle
    }
}