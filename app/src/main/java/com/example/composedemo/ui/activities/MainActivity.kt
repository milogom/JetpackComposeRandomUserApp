package com.example.composedemo.ui.activities

// Importaciones necesarias
import SetupNavGraph
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composedemo.R
import com.example.composedemo.theme.fontOswaldSemiBold
import com.example.composedemo.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

// Anotación que indica que esta actividad es un punto de entrada para la inyección de dependencias con Hilt.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // ViewModel que se inyecta automáticamente utilizando Hilt.
    private val viewModel: UserViewModel by viewModels()

    // Declaración de NavController para manejar la navegación entre composables.
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Inicialización del NavController para usar con Jetpack Compose.
            navController = rememberNavController()

            // Llamada a la función del ViewModel para obtener datos de usuario.
            viewModel.getAllUser()

            SetupNavGraph(navController = navController, viewModel = viewModel, context = this)

        }
    }
}
