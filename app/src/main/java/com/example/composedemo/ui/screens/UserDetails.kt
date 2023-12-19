package com.example.composedemo.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.composedemo.R
import com.example.composedemo.model.User
import com.example.composedemo.theme.fontHeeboMedium
import com.example.composedemo.theme.fontOswaldSemiBold
import com.example.composedemo.utils.*
import com.example.composedemo.viewmodel.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

@Composable
fun UserDetailsPreview(
    navController: NavHostController,
    viewModel: UserViewModel,
    context: Context,
) {
    viewModel.user?.let {
        UserDetailsScreenContent(
            navController = navController,
            user = it,
            context = context
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserDetailsScreenContent(
    navController: NavHostController,
    user: User,
    context: Context
) {

    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .background(Color.White)
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
    }

    Box()
    {
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
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
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    },
                    actions = {
                        // Ícono de menú desplegable
                        IconButton(onClick = { expanded.value = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }

                        // Menú desplegable
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                // Acción para "Favoritos"
                                expanded.value = false
                                context.ShowToast(context.getString(R.string.favourites_added))
                            }) {
                                Text(stringResource(R.string.favourite))
                            }
                        }
                    },
                    elevation = 0.dp,
                )
            },
        ) {
            // Contenido del Scaffold
        }
    }

    Box(
        modifier = Modifier
            .padding(20.dp, 150.dp, 8.dp, 4.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(end = 10.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = user.picture.large),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.White, CircleShape)
                )
                // Un Spacer con peso para empujar la siguiente imagen hacia la derecha
                Spacer(modifier = Modifier.weight(8f))

                // Segunda imagen (a la derecha y alineada abajo)
                Image(
                    painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(32.dp) // Ajusta el tamaño según sea necesario
                        .align(Alignment.Bottom) // Alinea esta imagen en la parte inferior del Row
                        .padding(bottom = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                // Segunda imagen (a la derecha y alineada abajo)
                Image(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(32.dp) // Ajusta el tamaño según sea necesario
                        .align(Alignment.Bottom) // Alinea esta imagen en la parte inferior del Row
                        .padding(bottom = 10.dp)
                )
            }



            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {

                // Sección Nombre y apellidos
                Card(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    elevation = 0.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_account_circle_24),
                            contentDescription = "User icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f) // Añadir weight aquí
                                .padding(27.dp, 5.dp, 0.dp, 15.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.name_surname),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.caption,
                                fontFamily = fontHeeboMedium,
                                color = Color.LightGray
                            )
                            Text(
                                text = user.name.getUsername(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body1,
                                fontFamily = fontHeeboMedium
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.padding(start = 60.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )

                // Sección email
                Card(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    elevation = 0.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 5.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_mail_outline_24),
                            contentDescription = "Email icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f) // Añadir weight aquí
                                .padding(27.dp, 5.dp, 0.dp, 15.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.email),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.caption,
                                fontFamily = fontHeeboMedium,
                                color = Color.LightGray
                            )
                            Text(
                                text = user.email,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body1,
                                fontFamily = fontHeeboMedium
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.padding(start = 60.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )

                // Sección Género
                Card(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    elevation = 0.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 5.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_female_24),
                            contentDescription = "Gender Icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f) // Añadir weight aquí
                                .padding(27.dp, 5.dp, 0.dp, 15.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.gender),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.caption,
                                fontFamily = fontHeeboMedium,
                                color = Color.LightGray
                            )
                            Text(
                                text = showGenderTranslated(context, user.gender),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body1,
                                fontFamily = fontHeeboMedium
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.padding(start = 60.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )

                // Sección Fecha de registro
                Card(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    elevation = 0.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 5.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                            contentDescription = "Date Icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f) // Añadir weight aquí
                                .padding(27.dp, 5.dp, 0.dp, 15.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.registered_date),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.caption,
                                fontFamily = fontHeeboMedium,
                                color = Color.LightGray
                            )
                            Text(
                                text = formatToShortDate(user.registered.getRegisteredDate()),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body1,
                                fontFamily = fontHeeboMedium
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.padding(start = 60.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )

                // Sección Teléfono
                Card(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    elevation = 0.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 5.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.baseline_phone_24),
                            contentDescription = "Phone Icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f) // Añadir weight aquí
                                .padding(27.dp, 5.dp, 0.dp, 15.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.phone),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.caption,
                                fontFamily = fontHeeboMedium,
                                color = Color.LightGray

                            )
                            Text(
                                text = user.phone,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body1,
                                fontFamily = fontHeeboMedium
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.padding(start = 60.dp),
                    color = Color.LightGray,
                    thickness = 1.dp
                )

                // Sección Coordenadas
                Card(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    elevation = 0.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 5.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.baseline_location_on_24),
                            contentDescription = "GPS Icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f) // Añadir weight aquí
                                .padding(27.dp, 5.dp, 0.dp, 15.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {

                            val latLng = LatLng(
                                user.location.getUserLatitude(),
                                user.location.getUserLongitude()
                            )

                            var mapView: MapView? = null
                            var googleMap: GoogleMap? by remember {
                                mutableStateOf<GoogleMap?>(
                                    null
                                )
                            }

                            AndroidView(
                                factory = { context ->
                                    MapView(context).apply {
                                        mapView = this
                                        onCreate(null)
                                        getMapAsync {
                                            googleMap = it
                                            it.moveCamera(
                                                CameraUpdateFactory.newLatLngZoom(
                                                    latLng,
                                                    10f
                                                )
                                            )
                                            it.addMarker(MarkerOptions().position(latLng))
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp), // Ajusta el tamaño como necesites
                                update = {
                                    mapView?.onResume()
                                }
                            )

                            Text(
                                text = stringResource(R.string.address),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.caption,
                                fontFamily = fontHeeboMedium,
                                color = Color.LightGray

                            )

                            Text(
                                text = user.location.getUserLocation(),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body1,
                                fontFamily = fontHeeboMedium
                            )

                            Text(
                                text = stringResource(R.string.coordinates),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.caption,
                                fontFamily = fontHeeboMedium,
                                color = Color.LightGray

                            )

                            Text(
                                text = user.location.getUserCoordinates(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body1,
                                fontFamily = fontHeeboMedium
                            )
                        }
                    }
                }
            }
        }
    }
}



