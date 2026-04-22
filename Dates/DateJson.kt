package com.example.myastjson_v3.Dates

import android.util.Log
import android.util.Log.d
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@JsonClass(generateAdapter = true) // Генерирует код адаптера при сборке
data class RootObject(
    val id: Int,
    val name: String,
    val long_name: String = "",
    var modeTU: Int = 0, // 0- Default нет связи, 1- Авто, 2- Руч.Вкл, 3- Руч.Откл,
    var status: Boolean = false,
    val status_alarm: Boolean = false,
    var elements: List<Element>,
)

data class MapPoint(
//    для координат Широта и Долгота
    val N: String = "",
    val W: String = "",
)

@JsonClass(generateAdapter = true) // Генерирует код адаптера при сборке
data class Element(
    val idp: Int,
    val namep: String,
    val modeWork: Boolean, // Обязательно val! иначе будет StackOverflow. false -ОТКЛ , true - ВКЛ
    val setpoint_light_point: Float = 0.0f, // точка установки
    val status_light_point: Int =0, // 0-нет питания, 1-вкл, 2-откл
    val status_alarm: Boolean = false,
    val map_point_id: MapPoint = MapPoint("0", "0"),
)

val rootSTR = """
[
  {
    "id": 1,
    "name": "ТП 1",
    "modeTU": 1,
    "status_alarm": false,
    "elements": [
      {
        "idp": 1,
        "namep": "sv160.1",
        "modeWork": false
      },
      {
        "idp": 2,
        "namep": "sv160.2",
        "modeWork": false
      },
      {
        "idp": 3,
        "namep": "sv160.3",
        "modeWork": false
      }
    ]
  },
  {
    "id": 2,
    "name": "ТП 2",
    "modeTU": 0,
    "status_alarm": true,
    "elements": [
      {
        "idp": 1,
        "namep": "sv160.1",
        "modeWork": false
      },
      {
        "idp": 2,
        "namep": "sv160.2",
        "modeWork": false
      }
    ]
  }
]
"""


class RootJson : ViewModel() {

    val rootList = mutableStateListOf<RootObject>() // список объектов RootObject


    fun readRoot() {
        val parsedData = parseData(rootSTR)
        if (parsedData != null) {
            // Чтобы не дублировать данные при повторном нажатии
            rootList.clear()
            rootList.addAll(parsedData)
        }
    }

    fun getRoot(): RootObject? = rootList.firstOrNull()

    fun getRootById(id: Int): RootObject? {
        return rootList.find { it.id == id }
    }

    fun setStatusOn(id: Int) {
        Log.d("setStatus", "Начинаем обновление для ID $id")
        // 1. Находим индекс
        val index = rootList.indexOfFirst { it.id == id }
        if (index != -1) {
            // Получаем текущий объект
            val currentRoot = rootList[index]
            // 2. СОЗДАЕМ НОВЫЙ СПИСОК ЭЛЕМЕНТОВ
            // Метод .map создаст новый список, где у каждого элемента будет вызван .copy
            val updatedElements = currentRoot.elements.map { element ->
                element.copy(modeWork = true)
            }
            // 3. ОБНОВЛЯЕМ ВЕСЬ КОРНЕВОЙ ОБЪЕКТ
            // Мы передаем и новый статус, и новый список элементов одновременно
            rootList[index] = currentRoot.copy(
                modeTU = 1,
                status = true,
                elements = updatedElements as MutableList<Element>
            )
            Log.d("setStatus", "Объект $id и все его элементы (${updatedElements.size}) обновлены!")
        }
    }

    fun setStatusOff(id: Int) {
        // 1. Находим индекс
        val index = rootList.indexOfFirst { it.id == id }
        if (index != -1) {
            // Получаем текущий объект
            val currentRoot = rootList[index]
            // 2. СОЗДАЕМ НОВЫЙ СПИСОК ЭЛЕМЕНТОВ
            // Метод .map создаст новый список, где у каждого элемента будет вызван .copy
            val updatedElements = currentRoot.elements.map { element ->
                element.copy(modeWork = false)
            }
            // 3. ОБНОВЛЯЕМ ВЕСЬ КОРНЕВОЙ ОБЪЕКТ
            // Мы передаем и новый статус, и новый список элементов одновременно
            rootList[index] = currentRoot.copy(
                modeTU = 2,
                status = false,
                elements = updatedElements as MutableList<Element>
            )
            Log.d("setStatus", "Объект $id и все его элементы (${updatedElements.size}) обновлены!")
        }
    }

    fun setOnlyOn(rootId: Int) {
        val rootIndex = rootList.indexOfFirst { it.id == rootId }
        if (rootIndex != -1) {
            rootList[rootIndex] = rootList[rootIndex].copy(modeTU = 1, status = true)
        }
    }

    fun setOnlyOff(rootId: Int) {
        val rootIndex = rootList.indexOfFirst { it.id == rootId }
        if (rootIndex != -1) {
            rootList[rootIndex] = rootList[rootIndex].copy(modeTU = 2, status = false)
        }
    }


    // один элемент управления ВКЛ
    fun toggleElementWorkOn(rootId: Int, elementId: Int) {
        val rootIndex = rootList.indexOfFirst { it.id == rootId }
        if (rootIndex != -1) {

            val updatedElements = rootList[rootIndex].elements.map { el ->
                if (el.idp == elementId) el.copy(modeWork = true) else el
            }
            rootList[rootIndex] =
                rootList[rootIndex].copy(elements = updatedElements as MutableList<Element>)
            setOnlyOn(rootId)
        }
    }

    // один элемент управления ВКЛ
    fun toggleElementWorkOff(rootId: Int, elementId: Int) {
        val rootIndex = rootList.indexOfFirst { it.id == rootId }
        if (rootIndex != -1) {
            val updatedElements = rootList[rootIndex].elements.map { el ->
                if (el.idp == elementId) el.copy(modeWork = false) else el
            }
            rootList[rootIndex] =
                rootList[rootIndex].copy(elements = updatedElements as MutableList<Element>)
        }
        setOnlyOff(rootId)
    }
    // ################################# GET #######################################
    // берем объект по ID из элемента и возвращаем его
    fun getElement(rootId: Int, elementId: Int): Element? {
        val rootIndex = rootList.indexOfFirst { it.id == rootId }
        if (rootIndex != -1) {
            return rootList[rootIndex].elements.find { it.idp == elementId }
        }
        return null
    }
    fun getAlarms(rootId: Int): Boolean {
        val rootIndex = rootList.indexOfFirst { it.id == rootId }
        if (rootIndex != -1) {
            return rootList[rootIndex].status_alarm
    }
        return false
    }

}

fun parseData(jsonString: String): List<RootObject>? {  // парсит JSON
    d("parseData", "parseData")

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    // Описываем тип: List<RootObject>
    val listType = Types.newParameterizedType(List::class.java, RootObject::class.java)

    //    Создаем адаптер для этого типа
    val adapter = moshi.adapter<List<RootObject>>(listType)
    //    Парсим JSON
    return try {
        adapter.fromJson(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}

/*
Как это выглядит в работе (логика)
Представьте, что Moshi работает как почтовый сортировщик:
Он видит внешний символ [ — понимает, что это List.
Внутри списка видит { — понимает, что это объект RootObject.
Читает id, name, modeTU.
Доходит до ключа "elements": [ — он видит, что в модели это List<Element2>,
 поэтому временно переключается на создание объектов Element2.
Заполнив список elements, он возвращается к сборке RootObject.
 */