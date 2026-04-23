package com.example.myastjson_v3.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myastjson_v3.Dates.RootJson
import com.example.myastjson_v3.Screens.Screen1.ScreenLevel1
import com.example.myastjson_v3.ui.theme.BgScreen


@Composable
fun Screen(viewModel: RootJson, onItemClick: (Int) -> Unit) {


    // главного экрана формами КП
    Scaffold(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize(),
        topBar = { Text("Верхняя панель") },
        bottomBar = { Text("Нижняя панель") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BgScreen),
        ) {

            Text("Screen")
            ScreenLevel1(viewModel, onItemClick)
            }
        }
    }


