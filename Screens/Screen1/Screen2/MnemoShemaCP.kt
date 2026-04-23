package com.example.myastjson_v3.Screens.Screen1.Screen2

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myastjson_v3.Dates.RootJson
import com.example.myastjson_v3.Dates.RootObject
import com.example.myastjson_v3.Screens.btn_Auto_Vkl_Otkl.ControlBtnScreen
import com.example.myastjson_v3.ui.theme.BgControlActive
import com.example.myastjson_v3.ui.theme.BgScreen
import com.example.myastjson_v3.ui.theme.MyBarColor
import com.example.myastjson_v3.ui.theme.MyBlack
import com.example.myastjson_v3.ui.theme.MyGrey
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// МНЕМОСХЕМА КП (по id)
@Composable
fun ScreenMnemoshema(rootId: Int, viewModel: RootJson, onBack: () -> Unit, onClickElement: (Int) -> Unit){

// Находим объект по ID
    val rootObject = viewModel.getRootById(rootId) // берем объект из viewModel

// форма управления мнемосхемы КП по id
    Scaffold(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize(),
        topBar = {Row(Modifier.fillMaxWidth().background(BgScreen)){
             Text("${rootObject?.long_name}")
            }
        },
        bottomBar = { }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BgScreen),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text("Mnemo")
            ItemScreenComposableCp(rootObject)
            Spacer(Modifier.height(16.dp))
            Text("кнопки управления КП")
            ControlBtnScreen(onClickBtn={it ->
                // нужна логика вкл, откл, авто
                Log.i("!!!", "кнопка управления КП нажата: $it")
                when(it){
                    0 -> viewModel.setStatusOn(rootId)
                    1 -> viewModel.setStatusOn(rootId)
                    2 -> viewModel.setStatusOff(rootId)
                }
            }
            )

        }

    }

}
@Composable
fun ItemScreenComposableCp(rootObject: RootObject?) {
    val cp = rootObject ?: return // для доступа к данным из WorkModel


    Box(
        modifier = Modifier
            .fillMaxHeight(0.6f)
            .fillMaxWidth()
            .background(BgScreen),
        contentAlignment = Alignment.Center
    ) {
        // Text and Line and Circle
        EightSlicesCircle()
        LinersGound()
        // status vkl
        if (cp.status) CirclesON() else CirclesOFF()
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .background(MyGrey)
                .clickable(
                    onClick = {
                        // открытие свето-точки список
//                        navController.navigate(Screen.openLightPointList.createRoute(cp.id))
                        Log.i("!!!", "ItemScreenComposableCp -> onClick: ${cp.id}")
                    }),
            contentAlignment = Alignment.Center  //  центр
        ) {
            Text("Светоточки", textAlign = TextAlign.Center, color = Color.White)
        }
    }
    //    КНОПКИ УПРАВЛЕНИЯ (АВТО, ВКЛ, ОТКЛ)
//    Column() {
//    ControlBtn(indexId,)
//    }


}
@Composable
fun CirclesOFF() {
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .clip(CircleShape)
    ) {
// drawARC
        val radius = size.minDimension / 3f
        val rect = Rect(center.x - radius, center.y - radius, center.x + radius, center.y + radius)

        for (i in 0..360 step 15) {
            drawArc(
                color = MyBarColor, // Цвет заливки
                startAngle = i.toFloat(),
                sweepAngle = 15f,
                useCenter = true,
                topLeft = Offset(rect.left, rect.top),
                size = Size(rect.width, rect.height)
            )
            // 2. ✅ КОНТУР (другой цвет)
            drawArc(
                color = MyBlack,         // Цвет рамки
                startAngle = i.toFloat(),
                sweepAngle = 15f,
                useCenter = true,               // Открытая дуга
                topLeft = Offset(rect.left, rect.top),
                size = Size(rect.width, rect.height),
                style = Stroke(
                    width = 2.dp.toPx(),         // Толщина рамки
                    cap = StrokeCap.Round
                )
            )


        }
    }


}

@Composable
fun CirclesON() {
    Canvas(
        modifier = Modifier
            .size(260.dp)
            .clip(CircleShape)
        // выравнивание по центру Ccanvas

    ) {
//
// drawARC
        val radius = size.minDimension / 2.2f
        val rect =
            Rect(center.x - radius, center.y - radius, center.x + radius, center.y + radius)
        for (i in 0..360 step 15) {
            drawArc(
                color = BgControlActive, // Цвет заливки
                startAngle = i.toFloat(),
                sweepAngle = 15f,
                useCenter = true,
                topLeft = Offset(rect.left, rect.top),
                size = Size(rect.width, rect.height)
            )
            // 2. ✅ КОНТУР (другой цвет)
            drawArc(
                color = MyBlack,         // Цвет рамки
                startAngle = i.toFloat(),
                sweepAngle = 15f,
                useCenter = true,               // Открытая дуга
                topLeft = Offset(rect.left, rect.top),
                size = Size(rect.width, rect.height),
                style = Stroke(
                    width = 2.dp.toPx(),         // Толщина рамки
                    cap = StrokeCap.Round
                )
            )
        }
    }


}


@OptIn(ExperimentalTextApi::class)
@Composable
fun EightSlicesCircle() {
    val listValueL = listOf("Л3", "Л4", "Л5", "Л6", "Л7", "Л8", "Л1", "Л2")
    Canvas(
        modifier = Modifier.size(450.dp)
//        .background(Color.White)
    ) {
        val center = Offset((size.width / 2) - 50f, (size.height / 2) + 40f)
        val radius = size.minDimension / 2 * 0.75f

        //  Линии + текст
        repeat(8) { index ->
            val angle = index * 45f * PI / 180f
            val endX = center.x + radius * cos(angle).toFloat()
            val endY = center.y + radius * sin(angle).toFloat()
//
//            // ЛИНИЯ
//            drawLine(
//                color = Color.Black,
//                start = center,
//                end = Offset(endX, endY),
//                strokeWidth = 2.dp.toPx()
//            )
            // ТЕКСТ (nativeCanvas)
            drawIntoCanvas { canvas ->
                val paint = Paint().asFrameworkPaint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 80f
//                    textAlign = Paint.Align.CENTER  // Центрирование!
                    typeface = android.graphics.Typeface.DEFAULT
                    isAntiAlias = true
                }

                // Рисуем текст точно у конца линии
                canvas.nativeCanvas.drawText(
                    listValueL[index],
                    endX,
                    endY,
                    paint
                )
            }
        }
    }
}


@Composable
fun LinersGound() {
    // Группа линий
    Canvas(
        modifier = Modifier
            .size(260.dp)
            .clip(CircleShape)
    ) {
        val radius = size.minDimension / 2f // /2 * 0.8f  // 80% радиус
        val center = Offset(size.width / 2, size.height / 2)

        //  Рисуем 8 секторов
        repeat(8) { index ->
            val angle = index * 45f * PI / 180f  // 0°, 45°, 90°... в радианы
            val endX = center.x + radius * cos(angle).toFloat()
            val endY = center.y + radius * sin(angle).toFloat()

            drawLine(
                color = Color.Black,
                start = center,
                end = Offset(endX, endY),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

/*
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

 */