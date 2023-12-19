// Importaciones necesarias para la navegación y la UI
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composedemo.navigation.AppScreens
import com.example.composedemo.ui.HomeScreenContent
import com.example.composedemo.ui.UserDetailsScreenContent
import com.example.composedemo.viewmodel.UserViewModel

// Define una función composable para configurar la navegación en la aplicación
@Composable
fun SetupNavGraph(
    navController: NavHostController, // Controlador de navegación para manejar la navegación entre pantallas
    viewModel: UserViewModel // ViewModel para acceder y manejar los datos de usuario
) {
    // NavHost define el contenedor de navegación y especifica la pantalla de inicio
    NavHost(navController = navController, startDestination = AppScreens.Home.route) {

        // Define una pantalla composable para la ruta "Home"
        composable(route = AppScreens.Home.route) {
            HomeScreenContent(
                navController = navController,
                viewModel = viewModel,
            )
        }

        // Define otra pantalla composable para la ruta "Details"
        composable(route = AppScreens.Details.route) {
            // UserDetailsPreview es el composable que se muestra para la pantalla de detalles
            UserDetailsScreenContent(
                navController = navController,
                viewModel = viewModel
            )
        }

    }
}
