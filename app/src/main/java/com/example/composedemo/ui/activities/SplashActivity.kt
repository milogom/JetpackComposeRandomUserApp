package com.example.composedemo.ui.activities

// Importaciones necesarias
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.composedemo.ui.screens.composables.SplashScreen
import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// La anotación @SuppressLint se utiliza para indicar que ignoraremos la advertencia
// "CustomSplashScreen". Esto se debe a que estamos creando una actividad de pantalla de inicio personalizada.
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    // El método onCreate se llama cuando la actividad está iniciando.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            var showSplashScreen by remember { mutableStateOf(true) }
            // Aquí definimos un LaunchedEffect para esperar un tiempo determinado
            LaunchedEffect(key1 = true) {
                delay(3000) // Espera 3 segundos
                showSplashScreen = false
            }

            if (showSplashScreen) {
                SplashScreen() // Muestra la pantalla de inicio
            } else {
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }
    }
}



