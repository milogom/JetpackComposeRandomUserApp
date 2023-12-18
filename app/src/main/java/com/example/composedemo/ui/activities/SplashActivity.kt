package com.example.composedemo.ui.activities

// Importaciones necesarias
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

// La anotación @SuppressLint se utiliza para indicar que ignoraremos la advertencia
// "CustomSplashScreen". Esto se debe a que estamos creando una actividad de pantalla de inicio personalizada.
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    // El método onCreate se llama cuando la actividad está iniciando.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instala la pantalla de inicio (splash screen). Esta es una función de la biblioteca de soporte
        // de Android que proporciona una forma estándar de implementar una pantalla de inicio.
        installSplashScreen()

        // setContent se usa en lugar de setContentView en actividades que utilizan Jetpack Compose.
        // Aquí no definimos UI con Compose, sino que lo usamos para iniciar otra actividad.
        setContent {
            // Creación de un Intent para iniciar MainActivity.
            val mainIntent = Intent(this, MainActivity::class.java)

            // Inicia MainActivity.
            startActivity(mainIntent)

            // Finaliza la SplashActivity. Esto evita que el usuario regrese a la pantalla de inicio
            // al presionar el botón de retroceso.
            finish()
        }
    }
}



