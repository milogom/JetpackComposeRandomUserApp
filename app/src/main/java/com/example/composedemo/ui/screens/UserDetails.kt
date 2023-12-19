package com.example.composedemo.ui

// Importaciones necesarias
import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.composedemo.R
import com.example.composedemo.model.User
import com.example.composedemo.theme.*
import com.example.composedemo.utils.*
import com.example.composedemo.viewmodel.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

@Composable
fun UserDetailsScreenContent(
    navController: NavHostController,
    viewModel: UserViewModel
) {
    viewModel.user?.let {
        UserDetailsContent(
            navController = navController,
            user = it,
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserDetailsContent(
    navController: NavHostController,
    user: User,
) {

    Image(
        painter = rememberAsyncImagePainter(R.drawable.sunset),
        contentDescription = "Imagen de cabecera",
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .fillMaxWidth() // Hace que la imagen ocupe el ancho completo
            .height(200.dp),
        contentScale = ContentScale.Crop
    )

    Scaffold(
        backgroundColor = Color.Transparent,
        topBar = { UserDetailsTopBar(navController, user) }
    ) {
        UserDetailsBody(user)
    }
}

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
                    painter = painterResource(id = R.drawable.baseline_chevron_left_24),
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


@Composable
fun UserDetailsBody(user: User) {
    Column(
        modifier = Modifier
            .padding(20.dp, 95.dp, 10.dp, 0.dp)
    ) {
        UserProfileHeader(user = user)
        Column(
            modifier = Modifier
                .padding(10.dp, 15.dp, 0.dp, 0.dp)
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

@Composable
fun UserProfileHeader(user: User) {
    Row(
        modifier = Modifier
            .padding(end = 10.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = user.picture.large),
            contentDescription = "User Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(4.dp, Color.White, CircleShape)
        )
        Spacer(modifier = Modifier.weight(5f))
        Image(
            painter = painterResource(id = R.drawable.baseline_photo_camera_24),
            contentDescription = "Camera Icon",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Bottom)
                .offset(y = (-10).dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.baseline_edit_24),
            contentDescription = "Camera Icon",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Bottom)
                .offset(y = (-10).dp)
        )
    }
}

@Composable
fun UserInfoSection(iconId: Int, title: String, content: String) {
    Card(
        modifier = Modifier
            .padding(8.dp, 0.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        shape = RectangleShape,
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = "Icon",
                modifier = Modifier.align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(30.dp, 10.dp, 0.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                CardTexts(label = title, content = content)
                Divider(
                    modifier = Modifier
                        .padding(0.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun UserMapSection(iconId: Int, user: User) {
    var googleMap: GoogleMap? by remember { mutableStateOf(null) }
    val latLng = LatLng(user.location.getUserLatitude(), user.location.getUserLongitude())
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = "Icon",
                modifier = Modifier.align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(27.dp, 5.dp, 0.dp, 15.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            onCreate(null)
                            getMapAsync {
                                googleMap = it
                                it.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(latLng, 10f)
                                )
                                it.addMarker(MarkerOptions().position(latLng))
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                CardTexts(stringResource(id = R.string.address), user.location.getUserLocation())
                CardTexts(stringResource(id = R.string.coordinates), user.location.getUserCoordinates())
            }
        }
    }


}

@Composable
fun CardTexts(label: String, content: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.caption,
        fontFamily = fontOpenSansMedium,
        color = Color.DarkGray
    )
    Text(
        text = content,
        maxLines = 2,
        style = MaterialTheme.typography.body1,
        fontFamily = fontOpenSansSemibold,
        color = Color.Black
    )
}
