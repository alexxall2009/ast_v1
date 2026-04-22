package com.example.myastjson_v3.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myastjson_v3.ui.theme.MyGreen
import com.example.myastjson_v3.ui.theme.MyRed
import com.example.myastjson_v3.ui.theme.MyTextColor

//@Preview
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val viewModelLogin: LoginVM = viewModel()
    val authResult = viewModelLogin.authResult
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
//                .background(color = MyBlue),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center

    ) {
        Text(
            "Авторизация", fontSize = 32.sp, color = MyTextColor
        )
        Text(
            "пользователя", fontSize = 32.sp, color = MyTextColor
        )
        Spacer(modifier = Modifier.height(32.dp))
        //  поля регистрации
        OutlinedTextField(
            value = viewModelLogin.inLogin,
            onValueChange = viewModelLogin::emailChange,  //{viewModel.emailChange(it)},
            label = { Text("Логин") },
            placeholder = { Text("Введите логин") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = viewModelLogin.inPass,
            onValueChange = viewModelLogin::passwordChange,//{it -> viewModel.passwordChange(it)},
            label = { Text("Пароль") },
            placeholder = { Text("Введите пароль") },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = viewModelLogin::actionTest,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Авторизоваться") }

        // Отображение результата
        authResult?.let { result ->
            when (result) {
                is AuthResult.Success -> {
                    Text("Успешная авторизация!", color = MyGreen)
                    viewModelLogin.resetAuthResult()
                    Toast.makeText(context, "Успешная авторизация!", Toast.LENGTH_SHORT).show()
                        // переход на следующий экран
                    onLoginSuccess()

                }

                is AuthResult.Error -> {
                    Text(result.message, color = MyRed)
                    viewModelLogin.resetAuthResult()
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()

                }
            }

        }


    }
}
