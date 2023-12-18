// Importaciones necesarias para la navegación y la UI
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composedemo.navigation.AppScreens
import com.example.composedemo.ui.HomeScreenPreview
import com.example.composedemo.ui.UserDetailsPreview
import com.example.composedemo.viewmodel.UserViewModel

// Define una función composable para configurar la navegación en la aplicación
@Composable
fun SetupNavGraph(
    navController: NavHostController, // Controlador de navegación para manejar la navegación entre pantallas
    viewModel: UserViewModel, // ViewModel para acceder y manejar los datos de usuario
    context: Context // Contexto de la aplicación o actividad
) {
    // NavHost define el contenedor de navegación y especifica la pantalla de inicio
    NavHost(navController = navController, startDestination = AppScreens.Home.route) {

        // Define una pantalla composable para la ruta "Home"
        composable(route = AppScreens.Home.route) {
            // HomeScreenPreview es el composable que se muestra para la pantalla de inicio
            HomeScreenPreview(
                navController = navController, // Pasa el NavController para manejar la navegación
                viewModel = viewModel, // Pasa el ViewModel para utilizar los datos
                context = context // Pasa el Contexto actual
            )
        }

        // Define otra pantalla composable para la ruta "Details"
        composable(route = AppScreens.Details.route) {
            // UserDetailsPreview es el composable que se muestra para la pantalla de detalles
            UserDetailsPreview(
                navController = navController, // Pasa el NavController
                viewModel = viewModel, // Pasa el ViewModel
                context = context // Pasa el Contexto
            )
        }

    }
}
