package com.example.myastjson_v3.Screens.btn_Auto_Vkl_Otkl


import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.myastjson_v3.ui.theme.BgScreen
import com.example.myastjson_v3.ui.theme.GrayBorder


data class BtnDate(
    val label: String = "",
    val Longlabel: String = "",
    val shape: RoundedCornerShape = RoundedCornerShape(50.dp),
    val bgColor: Color = BgScreen,
    val borderColor: Color = GrayBorder,
    val borderWidth: Int = 1,
    val isActive: Boolean = false,
    val hasFocus: Boolean = false,

    )


Магические строки "Авто"/"Вкл"/"Откл" — повторяются везде, легко опечататься. Нужен enum class Mode { AUTO, ON, OFF } с label как полем.

Индексы btn[0]/btn[1]/btn[2] — хрупкая привязка к порядку. Если кто-то поменяет порядок в списке, всё сломается молча. Лучше искать по enum/id.


class BtnModel : ViewModel() {

    val btn = mutableStateListOf( // так нужно
        BtnDate(label = "Авто", Longlabel = "Автоматическое управление", isActive = true, hasFocus = true),
        BtnDate(label = "Вкл", Longlabel = "Включение", isActive = false, hasFocus = false),
        BtnDate(label = "Откл", Longlabel = "Отключение", isActive = false, hasFocus = false)
    )

    fun updateBtn() { нужно переделать, нужно ли оно вообще?
        btn.forEach {
            it.isActive == false
            it.hasFocus == false
        }
    }
    fun isActive(action: String): Boolean {
        when (action) {
            "Авто" -> return btn[0].isActive == true
            "Вкл" -> return btn[1].isActive == true
            "Откл" -> return btn[2].isActive == true
            else -> {
                Log.i("!!!", "BtnModel -> isActive: $action")
                return false
            }
        }
    }

    fun isHasFocus(action: String): Boolean {
        when (action) {
            "Авто" -> return btn[0].isActive == true
            "Вкл" -> return btn[1].isActive == true
            "Откл" -> return btn[2].isActive == true
            else -> return false
        }
    }

    fun onClickAction(action: String) {
        when (action) {
            "Авто" -> setActiveAvto()
            "Вкл" -> setActiveVkl()
            "Откл" -> setActiveOtkl()
        }
    }

    fun setActiveAvto(){
        btn[0] = btn[0].copy(isActive = true, hasFocus = true)
        btn[1] = btn[1].copy(isActive = false, hasFocus = false)
        btn[2] = btn[2].copy(isActive = false, hasFocus = false)

    }
    fun setActiveVkl(){
        btn[0] = btn[0].copy(isActive = false, hasFocus = false)
        btn[1] = btn[1].copy(isActive = true, hasFocus = true)
        btn[2] = btn[2].copy(isActive = false, hasFocus = false)
    }
    fun setActiveOtkl(){
        btn[0] = btn[0].copy(isActive = false, hasFocus = false);
        btn[1] = btn[1].copy(isActive = false, hasFocus = false);
        btn[2] = btn[2].copy(isActive = true, hasFocus = true)
    }


}
