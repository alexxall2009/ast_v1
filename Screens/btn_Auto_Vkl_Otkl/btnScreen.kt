package com.example.myastjson_v3.Screens.btn_Auto_Vkl_Otkl

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myastjson_v3.ui.theme.BgScreen
import com.example.myastjson_v3.ui.theme.BluePrimary
import com.example.myastjson_v3.ui.theme.DarkText
import com.example.myastjson_v3.ui.theme.GrayBorder
import com.example.myastjson_v3.ui.theme.MyBluePrimary
import com.example.myastjson_v3.ui.theme.MylightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlBtnScreen(btn: BtnModel = viewModel(), onClickBtn: (Int) -> Unit) {

    var onClickBtnReturn by remember { mutableStateOf(0) }
    var btnState by remember { mutableStateOf("") } // какая кнопка была нажата
    var setPoint by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) } // окно подверждения
    Row(Modifier
        .fillMaxWidth()
        .background(BgScreen),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        btn.btn.forEachIndexed {index, it ->
            Button(
                onClick = {
                    btnState = it.label
                    setPoint = it.Longlabel
                    showBottomSheet = true
                    onClickBtnReturn = index

                },
                modifier = Modifier
                    .height(56.dp)
                    .border(
                        it.borderWidth.dp,
                        it.borderColor,
                        it.shape
                    )
                    .then(if (btn.isHasFocus(it.label)) Modifier.padding(2.dp) else Modifier),
                shape = it.shape,
                border = BorderStroke(1.dp, GrayBorder),
                colors = ButtonDefaults.buttonColors(it.bgColor)
            ) {
                Text(
                    it.label,
                    color = if (btn.isActive(it.label)) BluePrimary else DarkText,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false  // сброс ожидаемого действия при отмене
                    },
                    sheetState = sheetState,
                    containerColor = Color(0xFFF5F5F5),
                    tonalElevation = 10.dp,
//                dragHandle = { },
                ) {
                    ConfirmationPanel(
                        setPoint = setPoint,
                        onConfirm = {
                            Log.i("!!!", "onConfirm{}")
                            btn.onClickAction(btnState)
                            showBottomSheet = false
                            onClickBtn(onClickBtnReturn)
                        },
                        onDismiss = {
                            showBottomSheet = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationPanel(
    setPoint: String = "VKL",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var slideOffset by remember { mutableStateOf(0f) }
    var isLoading by remember { mutableStateOf(false) }

    val animatedValue by animateFloatAsState(
        targetValue = slideOffset,
        animationSpec = tween(durationMillis = 60),
        label = "sliderAnimation"
    )
    Box(modifier = Modifier.padding(20.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Подтверждение: ${setPoint}")
            Spacer(modifier = Modifier.height(20.dp))
            Slider(
                value = animatedValue,
                onValueChange = { newValue -> slideOffset = newValue },
                valueRange = 0f..100f,
                onValueChangeFinished = {
                    Log.i("!!!", "onValueChangeFinished: $slideOffset")
                    if (slideOffset >= 80) {
                        slideOffset = 100f
                        isLoading = true
                    } else {
                        slideOffset = 0f
                        isLoading = false
                    }
                },
                track = {
                    SliderTrack(
                        animatedValue,
                        isLoading,
                    ){onConfirm()}
                },
                thumb = { SliderThumb(slideOffset) }
            )
            Spacer(modifier = Modifier.height(13.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (!isLoading) {
                    Button(onClick = { onDismiss() }) {
                        Text("Отмена")
                    }
                }

            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun SliderThumb(x0: Float) {
    // Значения для иконки
    val imageVector = remember(key1 = x0) {
        if (x0 < 80) Icons.Filled.KeyboardArrowRight else Icons.Default.Check
    }

    Box(
        Modifier
            .size(60.dp)
            .border(
                width = 2.dp,
                color = MyBluePrimary,
                shape = RoundedCornerShape(50)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
//                .scale(pulse) // animate scale
                .clip(CircleShape)
                .background(MyBluePrimary), contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = imageVector,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
//
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderTrack(
    slideOffset: Float,  // Позиция слайдера (0f..1f)
    isLoading: Boolean = false, // команда принята
    onBack: () -> Unit,

    ) {

    val xStartR = remember(slideOffset) {
        slideOffset / 100f // нормализация
    }

    val textColors = if (isLoading) Color.White else Color.Black
    val textStandart = if (!isLoading) "Подтверждение " else "Подтверждение ..."

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(
                RoundedCornerShape(40.dp)
            )
    ) {
        val width = size.width
        val height = size.height
        val actualXStart = width * xStartR//(slideOffset / 100)

        drawRoundRect(
            color = MylightGray,//Color.LightGray,
//            topLeft = Offset(0f, 0f),
            size = Size(width, height),
            cornerRadius = CornerRadius(50f, 50f),
//            style = Fill
        )
        drawRoundRect(
            color = MyBluePrimary,
            size = Size(actualXStart, height),
            cornerRadius = CornerRadius(50f, 50f)
        )

    }
    Row(
        modifier = Modifier
            .offset(x = 60.dp, y = 0.dp)
    ) {
        Text(
            text = textStandart,
            color =textColors,//Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 7.dp)
        )
        if (isLoading) {
            Box(
                modifier = Modifier.size(40.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp), color = Color.White, strokeWidth = 5.dp
                )
                onBack()


            }
        }
    }
}