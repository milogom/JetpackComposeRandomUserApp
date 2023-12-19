package com.example.composedemo.ui

// Importaciones necesarias
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.composedemo.R
import com.example.composedemo.model.User
import com.example.composedemo.navigation.AppScreens
import com.example.composedemo.theme.fontHeeboMedium
import com.example.composedemo.theme.fontOswaldSemiBold
import com.example.composedemo.utils.getUsername
import com.example.composedemo.viewmodel.UserViewModel
import java.util.*

// Define un composable para la pantalla principal
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenPreview(
    navController: NavHostController,
    viewModel: UserViewModel,
    context: Context
) {
    val isLoading = viewModel.isLoading.value
    val userList = viewModel.userData.collectAsState().value

    val isSearchVisible by viewModel.isSearchVisible.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()


    // Scaffold utilizado para proporcionar una estructura básica de material design,
    // incluyendo una TopAppBar y un espacio para el contenido principal.
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = context.getString(R.string.app_name).uppercase(Locale.ROOT),
                        fontFamily = fontOswaldSemiBold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    // Icono de navegación y su acción
                    Image(
                        painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                        contentDescription = "image",
                        modifier = Modifier
                            .padding(15.dp, 0.dp, 0.dp, 0.dp)
                            .size(20.dp, 30.dp)
                            .clickable(
                                indication = rememberRipple(bounded = false),
                                interactionSource = remember { MutableInteractionSource() }) {
                                // Acción al hacer clic en el icono: regresar a la pantalla anterior.
                                navController.popBackStack()
                            },
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                },
                actions = {
                    // Ícono de menú desplegable
                    IconButton(onClick = {
                        viewModel.toggleSearchVisibility()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Menu"
                        )
                    }

                }, // fin de actions
            )
        },
    ) {
        // El contenido se coloca aquí dentro del lambda del Scaffold

        Column(
            modifier = Modifier.padding(it)
        ) {
            // Campo de búsqueda
            if (isSearchVisible) {
                TextField(
                    value = searchQuery,
                    onValueChange = { query -> viewModel.setSearchQuery(query) },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.White,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedLabelColor = Color.LightGray,
                        unfocusedLabelColor = Color.LightGray
                    ),
                    label = { Text(stringResource(id = R.string.search)) },
                    placeholder = { Text(stringResource(id = R.string.enter_name_or_email)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Ícono de búsqueda")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            // Aquí puedes definir lo que ocurre cuando se presiona 'Buscar' en el teclado
                            // Por ejemplo, cerrar el teclado
                            defaultKeyboardAction(ImeAction.Search)
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(100.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            // A continuación, muestra la lista de usuarios, que ahora reaccionará al cambio en searchQuery
            val userListToShow by if (searchQuery.isBlank()) {
                viewModel.userData // Lista completa si la consulta de búsqueda está vacía
            } else {
                viewModel.filteredUserData // Lista filtrada si hay una consulta de búsqueda
            }.collectAsState()

            if (isLoading) {
                ProgressBarComponent()
            } else {

                val filteredUserList by viewModel.filteredUserData.collectAsState()
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    userListToShow.forEach { userModel ->
                        items(userModel.results) { user ->
                            UserRow(user, viewModel, navController)
                        }
                    }
                }
            }
        }
    }
}


// Composable que define cómo se muestra cada fila de usuario en la lista
@Composable
fun UserRow(user: User, viewModel: UserViewModel, navController: NavHostController) {
    // Define una tarjeta (Card) para cada usuario
    Card(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            // Acción al hacer clic en la tarjeta
            .clickable(indication = rememberRipple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }) {
                viewModel.addUser(user) // Añade el usuario seleccionado al ViewModel
                navController.navigate(AppScreens.Details.route) // Navega a la pantalla de detalles
            },
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp, 15.dp, 1.dp, 11.dp)
                .fillMaxWidth()
        ) {
            // Muestra la imagen del usuario
            Image(
                painter = rememberAsyncImagePainter(model = user.picture.large),
                contentDescription = "image",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(60.dp)
                    .clip(CircleShape)
            )

            // Muestra información del usuario en una columna
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(27.dp, 5.dp, 0.dp, 15.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
                    text = user.name.getUsername(), // Utiliza una función de utilidad para obtener el nombre
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body1,
                    fontFamily = fontHeeboMedium
                )

                Text(
                    text = user.email,
                    maxLines = 1,
                    modifier = Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp),
                    style = MaterialTheme.typography.body2,
                    fontFamily = fontHeeboMedium,
                    color = Color.LightGray
                )

            }

            // Muestra un ícono de flecha hacia adelante
            Image(
                painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                contentDescription = "Forward Arrow",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(Color.LightGray)
            )

        }

        // Una línea divisora para separar cada fila de usuario
        Divider(
            modifier = Modifier.padding(start = 100.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

    }
}


