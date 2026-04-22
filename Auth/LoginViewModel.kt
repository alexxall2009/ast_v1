package com.example.myastjson_v3.Auth


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class LoginVM : ViewModel() {

    var inLogin  by mutableStateOf("")
        private set

    var inPass  by mutableStateOf("")
        private set

    // Состояние результата авторизации
    private var _authResult by mutableStateOf<AuthResult?>(null)
    val authResult: AuthResult? get() = _authResult

    fun emailChange(login: String) {inLogin = login }
    fun passwordChange(password: String) {inPass = password }

    fun isValidation(): Boolean {
        Log.d("LoginVM", "Login: $inLogin, Password: $inPass")
        return inLogin.isNotEmpty() && inPass.isNotEmpty()
    }

    fun clear() {
        inLogin = ""
        inPass = ""
    }
    fun actionTest() {
        if (isValidation()) {
            if (inLogin == "demo" && inPass == "demo") {
                Log.d("LoginVM", "Авторизация прошла успешно")
                _authResult = AuthResult.Success
                // переход на следующий экран

            } else {
                Log.d("LoginVM", "Неверный логин или пароль")
                _authResult = AuthResult.Error("Неверный логин или пароль")
            }
        } else {
            Log.d("LoginVM", "Поля не заполнены")
            _authResult = AuthResult.Error("Заполните все поля")
        }

    }
    // Сброс результата после отображения
    fun resetAuthResult() {
        _authResult = null
    }


}
// Тип результата авторизации
sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}