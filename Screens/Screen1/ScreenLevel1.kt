package com.example.myastjson_v3.Screens.Screen1

import android.util.Log
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myastjson_v3.Dates.RootJson


@Composable
fun ScreenLevel1(viewModel: RootJson, onItemClick: (Int) -> Unit) {

    //наполнение главного экрана формами из списка элементов управления КП
    Column(Modifier.fillMaxSize())
    {
        Text("FORM CP")

        LazyVerticalGrid( // создание таблицы из 2 столбцов
            columns = GridCells.Fixed(2), // Фиксировано 2 столбца
//            horizontalArrangement = Arrangement.spacedBy(5.dp), // отступы
//            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            contentPadding = PaddingValues(2.dp)
        ) {
            itemsIndexed(
                items = viewModel.rootList,
                key = { _, item -> item.id }
            ) { index, kp ->
                Column {
                    ItemsTableCP(
                        vm = viewModel,
                        id = kp.id,
                        onItemClickMnemo = { id ->
                            onItemClick(id)
                            Log.i("!!!", "ScreenLevel1 -> onItemClick: $id")
                        },
                    )

                }
            }

        }
    }
}


// ФОРМА ДЛЯ ГЛАВНОГО ЭКРАН КАРТОЧКАМИ КП
@Composable
fun ItemsTableCP(vm: RootJson, id: Int, onItemClickMnemo: (Int) -> Unit) {

    val cp = vm.getRootById(id)
    val modeTU = cp?.modeTU
    val textModeTustring = when (modeTU) {
        0 -> "Нет связи"
        1 -> "Авто"
        2 -> "Руч.Вкл"
        3 -> "Руч.Откл"
        else -> "Нет связи"
    }

    Card(
        onClick = {
            Log.i("!!!", "ItemsTableCP -> onClick: ${id}, ${vm.getRootById(id)?.name}")
            onItemClickMnemo(id)
        },
        modifier = Modifier
            .padding(5.dp)
            .size(160.dp, 100.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(1.dp), // подъем (типа тени)
        shape = RoundedCornerShape(10.dp) // фигура карты (закругление углов 10)
        ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${cp?.name}")
        // Линия-разделитель (опционально, для красоты)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Row(Modifier
                .fillMaxWidth(),
                horizontalArrangement = SpaceBetween) {
                Text("ID: ${cp?.id}")
                Spacer(Modifier.weight(1f))
                Text(textModeTustring)

            }
            Row(Modifier
                .fillMaxWidth(),
                horizontalArrangement = SpaceBetween) {
                Text("St: ${cp?.status}")
                Spacer(Modifier.weight(1f))
                Text("Al: ${cp?.status_alarm}")
            }
            Spacer(Modifier.weight(1f))
            Text("Группа NULL" )
        }
    }

}
