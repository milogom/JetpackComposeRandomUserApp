package com.example.composedemo.ui

// Importaciones necesarias
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composedemo.R
import com.example.composedemo.theme.fontOswaldSemiBold
import com.example.composedemo.ui.screens.composables.UserRow
import com.example.composedemo.viewmodel.UserViewModel
import java.util.*

// Define un composable para la pantalla principal
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    navController: NavHostController,
    viewModel: UserViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = { HomeTopAppBar(viewModel, context) },
        content = { HomeContainer(navController, viewModel) }
    )
}

@Composable
fun HomeTopAppBar(viewModel: UserViewModel, context: Context) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.White,
        title = {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(Locale.ROOT),
                fontFamily = fontOswaldSemiBold,
                color = Color.Black
            )
        },
        navigationIcon = {
            // Icono de navegación y su acción
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
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
        },
    )
}

// Composable que gestiona el contenedor del scaffold de home screen
@Composable
fun HomeContainer(navController: NavHostController, viewModel: UserViewModel) {
    val isLoading = viewModel.isLoading.value
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isSearchVisible by viewModel.isSearchVisible.collectAsState()
    Column(
    ) {
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
                        // Aquí se puede definir lo que ocurre cuando se presiona 'Buscar' en el teclado
                        // Por ejemplo, cerrar el teclado
                        defaultKeyboardAction(ImeAction.Search)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(100.dp, 0.dp, 0.dp, 0.dp)
            )
        }

        // A continuación, muestra la lista de usuarios, que reaccionará al cambio en searchQuery
        val userListToShow by if (searchQuery.isBlank()) {
            viewModel.userData // Lista completa si la consulta de búsqueda está vacía
        } else {
            viewModel.filteredUserData // Lista filtrada si hay una consulta de búsqueda
        }.collectAsState()

        if (isLoading) {
            ProgressBarComponent()
        } else {
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


