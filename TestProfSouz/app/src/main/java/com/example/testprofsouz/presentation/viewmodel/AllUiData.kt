package com.example.testprofsouz.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.example.testprofsouz.data.DBWriterReader
import com.example.testprofsouz.domain.model.DownTimeInfo
import com.example.testprofsouz.presentation.ui.NavigationGraph
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllUiData @Inject constructor() {

    private var navController: NavHostController? = null
    internal val titleTopBar = mutableStateOf("Добавить простой")
    internal val searchAvailable = mutableStateOf(false)
    internal val searchText = mutableStateOf("")

    @Inject
    lateinit var databaseWriterReader: DBWriterReader

    private val listPlot = listOf(
        listOf(
            "Рудник Скалистый",
            listOf("ПУБР", listOf("Газовая камера")),
            listOf(
                "Техническая служба",
                listOf("ПУБР 77", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа")),
                listOf(
                    "ПУОР",
                    listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа")
                ),
                listOf("ПУБР 12", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа")),
                listOf("ПУБР 15", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа"))
            ),
            listOf("ПУБР 32", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа")),
            listOf("ПУБР 4", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа"))
        ),
        listOf(
            "Рудник Скалистый 2",
            listOf("ПУБР", listOf("Газовая камера")),
            listOf(
                "Техническая служба",
                listOf("ПУБР 77", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа")),
                listOf("ПУОР", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа")),
                listOf("ПУБР 12", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа")),
                listOf("ПУБР 15", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа"))
            ),
            listOf("ПУБР 32", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа")),
            listOf("ПУБР 4", listOf("Очистная камера 1 этажа", "Очистная камера 2 этажа"))
        ),
        listOf(
            "Рудник Скалистый 3",
            listOf("ПУБР", listOf("Газовая камера"))
        )
    )

    private val listTypeDowntime = listOf(
        listOf(
            "Организационно-технические проблемы",
            listOf("Мастер забухал", "Машинист забухал", "Все пьют - присоединяйся!")
        ),
        listOf(
            "Некачественный ремонт оборудования",
            listOf(
                "Какой то дятел уронил гайку в редуктор",
                listOf("Машинист", "Мастер", "Помощник")
            )
        )
    )

    var currentList: MutableList<Any>? = null
    var currentId: Int? = null

    fun search(searchStr: String) {
        searchText.value = searchStr
    }

    fun setList(cascadeType: String) {
        when (cascadeType) {
            DownTimeEquipmentEnum.PLOT.showName, DownTimeEquipmentEnum.PLACE.showName -> {
                currentList = listPlot.toMutableList()
                currentId = 1
                titleTopBar.value = "Выбрать участок"
            }

            DownTimeEquipmentEnum.TYPE.showName -> {
                currentList = listTypeDowntime.toMutableList()
                currentId = 2
                titleTopBar.value = "Выбрать тип простоя"
            }

            DownTimeEquipmentEnum.REASON.showName -> {
                currentList = listTypeDowntime.toMutableList()
                currentId = 3
                titleTopBar.value = "Выбрать причину простоя"
            }

            DownTimeEquipmentEnum.EQUIPMENT.showName -> {
                currentList = listTypeDowntime.toMutableList()
                currentId = 4
                titleTopBar.value = "Выбрать простаивающее оборудование"
            }

            else -> {
                titleTopBar.value = "Добавить простой"
            }
        }
        navigateTo(NavigationGraph.CascadeDropScreen.name)
        searchAvailable.value = true
    }

    fun setScreen(navHostController: NavHostController) {
        navController = navHostController
    }

    fun navigateTo(screen: String = NavigationGraph.DownTimeScreen.name) {
        searchText.value = ""
        navController?.navigate(screen)
        if (screen == NavigationGraph.DownTimeScreen.name) {
            titleTopBar.value = "Добавить простой"
            searchAvailable.value = false
        }
    }

    fun setField(screenResponse: DownTimeInfo) {
        databaseWriterReader.getNewDataFromUser(screenResponse)
    }
}

enum class DownTimeEquipmentEnum(val showName: String) {

    PLOT("Участок"), PLACE("Место"), TYPE("Тип простоя"), REASON("Добавить причину простоя"),
    EQUIPMENT("Добавить оборудование"), COMMENT("Комментарий"), DATESTART("Дата начала"), TIMESTART(
        "Время начала"
    ),
    DATEEND("Дата окончания"), TIMEEND("Время окончания");
}
