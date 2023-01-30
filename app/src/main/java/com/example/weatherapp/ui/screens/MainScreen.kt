package com.example.weatherapp.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.time.ZoneOffset.UTC
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun MainScreen(
    vm: MainViewModel = koinViewModel()
) {


    val state by vm.viewState.collectAsState()
    val checkedStateRu = remember { mutableStateOf(false) }
    val checkedStateUsa = remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val config = LocalConfiguration.current
    val resources = LocalContext.current.resources
    val latitude = state.lat
    val longitude = state.lon

    var server by remember { mutableStateOf("weatherApi") }

    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    sdf.timeZone = TimeZone.getTimeZone(UTC)
    var lastUpdate by remember { mutableStateOf("") }

    if (latitude != null && longitude != null && server == "weatherApi") {
        vm.getWeatherApiTemp(lat = latitude, lon = longitude)
        lastUpdate = sdf.format(Date())
    }
    if (latitude != null && longitude != null && server == "openWeather") {
        vm.getOpenWeatherTemp(lat = latitude, lon = longitude)
        lastUpdate = sdf.format(Date())
    }
    if (latitude != null && longitude != null && server == "weatherVisual") {
        vm.getWeatherVisualTemp(lat = latitude, lon = longitude)
        lastUpdate = sdf.format(Date())
    }


    Surface {

        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box() {
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Показать меню")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Text(
                        "Сервер1",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable(onClick = {
                                if (latitude != null && longitude != null) {
                                    vm.getWeatherApiTemp(lat = latitude, lon = longitude)
                                    server = "weatherApi"
                                }
                            })
                    )
                    Divider()
                    Text(
                        "Сервер2",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable(onClick = {
                                if (latitude != null && longitude != null) {
                                    vm.getOpenWeatherTemp(lat = latitude, lon = longitude)
                                    server = "openWeather"
                                }
                            })
                    )
                    Divider()
                    Text(
                        "Сервер3",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable(onClick = {
                                if (latitude != null && longitude != null) {
                                    vm.getWeatherVisualTemp(lat = latitude, lon = longitude)
                                    server = "weatherVisual"
                                }
                            })
                    )
                }
            }
        }
        Column(

        ) {
            Row {
                Text(
                    text = stringResource(R.string.temperature)
                )
                Text(
                    text = state.temperature.toString()
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.city)

                )
                Text(
                    text = state.city.toString()
                )
            }
            Row {
                Text(
                    stringResource(R.string.last_update)

                )
                Text(
                    text = lastUpdate
                )
            }

            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Иконка с погодой"
            )
            Button(
                onClick = {
//                    context.createConfigurationContext(context.resources.configuration)
//                    context.findActivity()?.recreate()
//                    Log.d("TAG-MAINSCREEN", "button clicked")
                    if (latitude != null && longitude != null) {
                        when (server) {
                            "weatherApi" -> {
                                vm.getWeatherApiTemp(lat = latitude, lon = longitude)
                                lastUpdate = sdf.format(Date())
                            }
                            "openWeather" -> {
                                vm.getOpenWeatherTemp(lat = latitude, lon = longitude)
                                lastUpdate = sdf.format(Date())
                            }
                            "weatherVisual" -> {
                                vm.getWeatherVisualTemp(lat = latitude, lon = longitude)
                                lastUpdate = sdf.format(Date())
                            }
                        }
                    }
                }
            ) {
                Text(text = stringResource(R.string.button_update))
            }
            Row {
                Checkbox(
                    checked = checkedStateUsa.value,
                    onCheckedChange = {
                        config.setLocale(Locale("en"))
                        resources.updateConfiguration(
                            context.resources.configuration,
                            resources.displayMetrics
                        )
                        checkedStateUsa.value = it
                        checkedStateRu.value = false


                    },
                )
                Icon(
                    painter = painterResource(R.drawable.flag_usa),
                    contentDescription = "Кнопка с иконкой США",
                    tint = Color.Unspecified
                )


            }
            Row {
                Checkbox(
                    checked = checkedStateRu.value,
                    onCheckedChange = {
                        config.setLocale(Locale("ru"))
                        resources.updateConfiguration(
                            context.resources.configuration,
                            resources.displayMetrics
                        )
                        checkedStateRu.value = it
                        checkedStateUsa.value = false
                    },
                )
                Icon(
                    painter = painterResource(R.drawable.flag_russia),
                    contentDescription = "Кнопка с иконкой России",
                    tint = Color.Unspecified
                )
            }

        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
