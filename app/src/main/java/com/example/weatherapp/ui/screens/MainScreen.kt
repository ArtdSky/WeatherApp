package com.example.weatherapp.ui.screens

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
fun MainScreen(
    vm: MainViewModel = koinViewModel()
) {
    val checkedStateRu = remember { mutableStateOf(false) }
    val checkedStateUsa = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val config = LocalConfiguration.current
    val resources = LocalContext.current.resources


    Surface {
        Column(

        ) {
            Text(
                text = stringResource(R.string.temperature)
            )
            Text(
                text = stringResource(R.string.city)
            )
            Text(
                text = stringResource(R.string.last_update)
            )
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Иконка с погодой"
            )
            Button(
                onClick = {
                    context.createConfigurationContext(context.resources.configuration)
                    context.findActivity()?.recreate()
                    Log.d("TAG-MAINSCREEN", "button clicked")
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
