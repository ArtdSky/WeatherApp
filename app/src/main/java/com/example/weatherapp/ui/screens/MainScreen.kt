package com.example.weatherapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    vm : MainViewModel = koinViewModel()
) {
    val checkedState = remember { mutableStateOf(false) }
    Surface {
        Column(

        ) {
            Text(
                text = "Текст с информацией о текущей температуре"
            )
            Text(
                text = "Текст с информацией о городе"
            )
            Text(
                text = "Текст с информацией, когда было последнее обновление данных"
            )
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Иконка с погодой"
            )
            Button(
                onClick = {
                    Log.d("TAG-MAINSCREEN", "button clicked")
                }
            ) {
                Text(text = "Обновить")
            }
            Row {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
                )
                Icon(
//                    imageVector = ImageVector.vectorResource(id = R.drawable.flag_united_states),
                    painter = painterResource(R.drawable.flag_usa),
                    contentDescription = "Кнопка с иконкой США",
                    tint = Color.Unspecified
                )


            }
            Row {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
                )
                Icon(
                    painter = painterResource( R.drawable.flag_russia),
                    contentDescription = "Кнопка с иконкой России",
                    tint = Color.Unspecified
                )
            }

        }
    }
}