package com.example.composedemo.ui

// Importaciones necesarias
import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.composedemo.R
import com.example.composedemo.model.User
import com.example.composedemo.theme.*
import com.example.composedemo.ui.screens.composables.UserInfoSection
import com.example.composedemo.ui.screens.composables.UserMapSection
import com.example.composedemo.ui.screens.composables.UserProfileHeader
import com.example.composedemo.utils.*
import com.example.composedemo.viewmodel.UserViewModel
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserDetailsScreenContent(
    navController: NavHostController,
    viewModel: UserViewModel
) {
    viewModel.user?.let { user ->
        SetHeaderImage()
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = { UserDetailsTopBar(navController, user) }
        ) {
            UserDetailsBody(user)
        }
    }
}

@Composable
fun SetHeaderImage() {
    Image(
        painter = rememberAsyncImagePainter(R.drawable.sunset),
        contentDescription = "Imagen de cabecera",
        modifier = Modifier
            .fillMaxWidth() // Hace que la imagen ocupe el ancho completo
            .height(180.dp),
        contentScale = ContentScale.Crop
    )
}

// Composable de la TopAppBar del scaffold
@Composable
fun UserDetailsTopBar(navController: NavHostController, user: User) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        title = {
            Text(
                text = user.name.getUsername().uppercase(Locale.ROOT),
                fontFamily = fontOswaldSemiBold,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    // Acción para "Favoritos"
                }) {
                    Text(stringResource(R.string.favourite))
                }
            }
        },
        elevation = 0.dp
    )
}

// Composable del body del scaffold
@Composable
fun UserDetailsBody(user: User) {
    Column(
        modifier = Modifier
            .padding(15.dp, 80.dp, 10.dp, 0.dp)
    ) {
        UserProfileHeader(user = user)
        Column(
            modifier = Modifier
                .padding(start =  10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            UserInfoSection(
                iconId = R.drawable.baseline_account_circle_24,
                title = stringResource(id = R.string.name_surname),
                content = user.name.getUsername()
            )
            UserInfoSection(
                iconId = R.drawable.baseline_mail_outline_24,
                title = stringResource(id = R.string.email),
                content = user.email
            )
            UserInfoSection(
                iconId = R.drawable.baseline_female_24,
                title = stringResource(id = R.string.gender),
                content = showGenderTranslated(user.gender)
            )
            UserInfoSection(
                iconId = R.drawable.baseline_calendar_today_24,
                title = stringResource(id = R.string.registered_date),
                content = formatToShortDate(user.registered.getRegisteredDate())
            )
            UserInfoSection(
                iconId = R.drawable.baseline_phone_24,
                title = stringResource(R.string.phone),
                content = user.phone
            )
            UserMapSection(
                iconId = R.drawable.baseline_location_on_24,
                user
            )
        }
    }
}