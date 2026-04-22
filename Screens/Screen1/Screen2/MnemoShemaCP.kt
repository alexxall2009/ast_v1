package com.example.myastjson_v3.Screens.Screen1.Screen2

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myastjson_v3.Dates.RootJson


@Composable
fun ScreenMnemoshema(rootId: Int, viewModel: RootJson, onBack: () -> Unit, onClickElement: (Int) -> Unit){

// Находим объект по ID
    val rootObject = viewModel.getRootById(rootId) // берем объект из viewModel

    var colorStatusCp = when{
        rootObject?.status == true -> Color.Red
        rootObject?.status == false -> Color.Green
        else -> Color.Gray
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) { Text("Назад") }
        Spacer(Modifier.height(16.dp))
        if (rootObject != null) {
            Text("Детали объекта: ${rootObject.name}", style = MaterialTheme.typography.headlineMedium)
            Text("ID: ${rootObject.id}")
            Text("Статус: ${if (rootObject.status) "Активен" else "Неактивен"}",
                color =colorStatusCp)

            Spacer(Modifier.height(16.dp))
            Text("Элементы внутри:", style = MaterialTheme.typography.titleLarge)


            rootObject.elements.forEach { el ->
                Card(onClick = {
                    onClickElement(el.idp)
                    Log.d("ScreenMnemoshema", "Нажата кнопка ${el.namep}")
                },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(
                        text = "${el.namep} (Работа: ${el.modeWork})",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        } else {
            Text("Объект не найден")
        }
        Row(){
           Button(onClick = {viewModel.setStatusOn(rootId)
           }) {Text("True")}
           Button(onClick = {viewModel.setStatusOff(rootId)
           }) {Text("False")}
        }
    }

}