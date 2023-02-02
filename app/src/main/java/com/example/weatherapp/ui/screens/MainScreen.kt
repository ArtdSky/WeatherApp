package com.example.weatherapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.weatherapp.ui.theme.Purple200
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

    val sdf = SimpleDateFormat("dd.MM.yy HH:mm:ss")
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
    var image by remember { mutableStateOf(R.drawable.temp_neutral) }
    state.temperature?.let {
        if (-10 < it.toDouble() && it.toDouble() < 10) {
            image = R.drawable.temp_neutral
        }
        if (-10 > it.toDouble()) {
            image = R.drawable.temp_cold
        }
        if (it.toDouble() > 10) {
            image = R.drawable.temp_cold
        }
    }


    Surface(
        color = Purple200,
        modifier = Modifier.fillMaxSize()

    ) {


        //Список серверов
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

        //Температура и Инфа и иконка
        Column(
            verticalArrangement = Arrangement.Center
        ) {

            //Температура
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.temperature).replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                )
                Text(
                    text = state.temperature.toString(),
                    modifier = Modifier
                        .padding(start = 12.dp)
                )
            }

            //Инфа с иконкой
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                // Инфа
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.city).replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                }

                            )
                            Text(
                                text = state.city.toString(),
                                modifier = Modifier
                                    .padding(start = 12.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                stringResource(R.string.last_update).replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                }

                            )
                            Text(
                                text = lastUpdate,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                            )
                        }
                        Button(
                            onClick = {

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
                    }
                }
                // Иконка
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .size(100.dp)
                ) {
                    Icon(
                        contentDescription = "Иконка с погодой",
                        painter = painterResource(image),
                        tint = Color.Unspecified

                    )
                }

            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Row {
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
}


