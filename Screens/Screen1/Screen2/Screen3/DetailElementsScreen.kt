package com.example.myastjson_v3.Screens.Screen1.Screen2.Screen3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myastjson_v3.Dates.RootJson


@Composable
fun DetailElementScreen(rootId: Int, elementId: Int, viewModel: RootJson, onBack: () -> Unit) {


    Column(
    ) {
        Text("Элемент Экран")
        Button(onClick = onBack) { Text("Назад") }
        Spacer(Modifier.height(16.dp))
        Text("Корневой объект: $rootId")
        Text("Статус: ${viewModel.getRootById(rootId)?.status}")
        Text("${viewModel.getRootById(rootId)?.name}/${viewModel.getElement(rootId, elementId)?.namep}")
        Spacer(Modifier.height(30.dp))
        Row() {
            Button(onClick = { viewModel.toggleElementWorkOn(rootId, elementId) }) { Text("ВКЛ") }
            Button(onClick = { viewModel.toggleElementWorkOff(rootId, elementId) }) { Text("ВЫКЛ") }
        }

        Spacer(Modifier.height(16.dp))
        Text("Элемент: $elementId")
        Text("Статус: ${viewModel.getRootById(rootId)?.elements?.find { it.idp == elementId }?.modeWork}")


    }
}