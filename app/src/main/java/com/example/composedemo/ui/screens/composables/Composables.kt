package com.example.composedemo.ui.screens.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.composedemo.R
import com.example.composedemo.model.User
import com.example.composedemo.navigation.AppScreens
import com.example.composedemo.theme.fontOpenSansRegular
import com.example.composedemo.theme.fontOpenSansSemibold
import com.example.composedemo.utils.*
import com.example.composedemo.viewmodel.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun CardTexts(label: String, content: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.caption,
        fontFamily = fontOpenSansRegular,
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

@Composable
fun SimpleContentText(content: String) {
    Text(
        text = content,
        maxLines = 2,
        style = MaterialTheme.typography.body1,
        fontFamily = fontOpenSansSemibold,
        color = Color.Black
    )
}

@Composable
fun SimpleLabelText(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.caption,
        fontFamily = fontOpenSansRegular,
        color = Color.LightGray
    )
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
                SimpleLabelText(stringResource(id = R.string.address))

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
                SimpleLabelText(label = user.location.getUserLocation())
                SimpleLabelText(label = user.location.getUserCoordinates())
            }
        }
    }
}

@Composable
fun UserInfoSection(iconId: Int, title: String, content: String) {
    Card(
        modifier = Modifier
            .padding(8.dp, 15.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        shape = RectangleShape,
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
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
                    .padding(start = 30.dp),
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
fun UserProfileHeader(user: User) {
    Row(
        modifier = Modifier
            .padding(end = 10.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = user.picture.large),
            contentDescription = "User Image",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(4.dp, Color.White, CircleShape)
        )
        Spacer(modifier = Modifier.weight(7f))
        Image(
            painter = painterResource(id = R.drawable.baseline_photo_camera_24),
            contentDescription = "Camera Icon",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Bottom)
                .offset(y = (-4).dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.baseline_edit_24),
            contentDescription = "Edit Icon",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Bottom)
                .offset(y = (-4).dp)
        )
    }
}

// Composable que define cómo se muestra cada fila de usuario en la lista
@Composable
fun UserRow(user: User, viewModel: UserViewModel, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            // Acción al hacer clic en la tarjeta
            .clickable(indication = rememberRipple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }) {
                viewModel.addUser(user)
                navController.navigate(AppScreens.Details.route)
            },
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp, 10.dp, 1.dp, 5.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = user.picture.large),
                contentDescription = "image",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(27.dp, 5.dp, 0.dp, 0.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                SimpleContentText(content = user.name.getUsername())
                SimpleLabelText(label = user.email)
            }
            Image(
                painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                contentDescription = "Forward Arrow",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(Color.LightGray)
            )
        }
    }
    Divider(
        modifier = Modifier
            .padding(start = 105.dp),
        color = Color.LightGray,
        thickness = 1.dp
    )
}