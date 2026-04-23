package com.example.myastjson_v3.Screens.Screen1

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myastjson_v3.Dates.RootJson
import com.example.myastjson_v3.ui.theme.MyButtonColor


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
    val colorStatus = when (cp?.status) {
        true -> Color.Green
        false -> Color.Red
        null -> Color.Gray
    }

    // кол-во элементов управления светоточек
    val countElements = cp?.elements?.count()
    // проверяем сколько имеют статус вкл
    val countElementsOn  = cp?.elements?.count { it.modeWork }

    Card(
        onClick = {
            Log.i("!!!", "ItemsTableCP -> onClick: ${id}, ${vm.getRootById(id)?.name}")
            onItemClickMnemo(id)
        },
        modifier = Modifier
            .padding(5.dp)
//            .size(160.dp, 100.dp),
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(1.dp), // подъем (типа тени)
        shape = RoundedCornerShape(10.dp) // фигура карты (закругление углов 10)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                // Выравнивает всех детей (Box и Text) по центру их высоты
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp, 10.dp)
                        .background(color = colorStatus, shape = CircleShape)
                ) {}
                Spacer(modifier = Modifier.width(8.dp)) // Небольшой отступ между кругом и текстом
                Text("${cp?.name}")
                Spacer(modifier = Modifier.width(8.dp)) // Небольшой отступ между кругом и текстом
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "arrow")
            }
            // Линия-разделитель (опционально, для красоты)
            HorizontalDivider(modifier = Modifier.padding(vertical = 3.dp))
            Spacer(Modifier.weight(1f))
            Row(
                horizontalArrangement = SpaceBetween,
            ) {
                Text(
                    textModeTustring,
                    modifier = Modifier
                        .background(color = MyButtonColor, shape = RoundedCornerShape(10.dp))
                        .padding(// Внутренние отступы, чтобы текст «дышал»
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                )
                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .background(color = MyButtonColor, shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp), // Отступы внутри всей плашки
                    verticalAlignment = Alignment.CenterVertically // Центрируем иконку и текст
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bolt, // Ваша иконка
                        contentDescription = null,
                        modifier = Modifier.size(14.dp), // Делаем иконку маленькой под стать тексту
                        tint = Color.White
                    )
                    Spacer(Modifier.width(4.dp)) // Зазор между иконкой и текстом
                    Text(
                        text = " ${countElementsOn}/${countElements}", // до вкл
                        color = Color.White,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

        }
        Spacer(Modifier.weight(1f))
        Text("Группа NULL")
    }
}


