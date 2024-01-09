package com.example.testprofsouz.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testprofsouz.domain.model.DownTimeInfo
import com.example.testprofsouz.presentation.ui.NavigationGraph
import dagger.hilt.android.qualifiers.ActivityContext
import java.text.SimpleDateFormat
import javax.inject.Inject


class MainScreenViewModel @Inject constructor(
    private val dataUi: AllUiData,
    @ActivityContext val context: Context
) : ViewModel() {

    internal val areaField = mutableStateOf("")
    internal val placeField = mutableStateOf("")
    internal val typePlateField = mutableStateOf("")
    internal val reasonPlateField = mutableStateOf("")
    internal val equipmentField = mutableStateOf("")
    internal val dateStart = mutableStateOf("")
    internal val dateEnd = mutableStateOf("")
    internal val timeStart = mutableStateOf("")
    internal val timeEnd = mutableStateOf("")
    internal val comment = mutableStateOf("")
    private var timeStartInMillisecond: Long = 0
    private var datePickerDialog: DatePickerDialog? = null
    private var timePickerDialog: TimePickerDialog? = null
    private var selectedTime = ""

    fun setPickerDialog(pickerDialog: DatePickerDialog, timeDialog: TimePickerDialog) {
        datePickerDialog = pickerDialog
        timePickerDialog = timeDialog
    }

    fun selectDate(date: String) {
        selectedTime = date
        when (date) {
            DownTimeEquipmentEnum.DATESTART.showName -> datePickerDialog?.show()
            DownTimeEquipmentEnum.DATEEND.showName -> {
                datePickerDialog?.show()
                datePickerDialog?.datePicker?.minDate = timeStartInMillisecond
            }

            DownTimeEquipmentEnum.TIMESTART.showName -> timePickerDialog?.show()
            DownTimeEquipmentEnum.TIMEEND.showName -> {
                timePickerDialog?.show()

            }

            else -> selectCurrentPage(date)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat("dd.MM.yy")

    @SuppressLint("SimpleDateFormat")
    private val formatTime = SimpleDateFormat("HH:mm")

    fun addDate(date: Long) {
        when (selectedTime) {
            DownTimeEquipmentEnum.DATESTART.showName -> {
                dateStart.value = format.format(date)
                timeStartInMillisecond = date
            }

            DownTimeEquipmentEnum.DATEEND.showName -> dateEnd.value = format.format(date)
        }
    }

    fun addTime(time: Long) {
        when (selectedTime) {
            DownTimeEquipmentEnum.TIMESTART.showName, DownTimeEquipmentEnum.DATESTART.showName -> {
                timeStart.value = formatTime.format(time)
            }

            DownTimeEquipmentEnum.TIMEEND.showName, DownTimeEquipmentEnum.DATEEND.showName -> {
                timeEnd.value = formatTime.format(time)
            }
        }
    }

    private fun selectCurrentPage(page: String) {
        dataUi.setList(page)
    }

    private fun moveUp() {
        dataUi.navigateTo(NavigationGraph.DownTimeScreen.name)
    }

    fun setResponseField(fieldResponse: String, mainFieldResponse: String = "") {
        when (dataUi.currentId) {
            1 -> {
                areaField.value = mainFieldResponse
                placeField.value = fieldResponse
            }

            2 -> typePlateField.value = fieldResponse
            3 -> reasonPlateField.value = fieldResponse
            4 -> equipmentField.value = fieldResponse
        }
        moveUp()
    }

    fun setResponseScreen() {
        dataUi.setField(
            DownTimeInfo(
                areaField.value,
                placeField.value,
                typePlateField.value,
                reasonPlateField.value,
                equipmentField.value,
                dateStart.value,
                dateEnd.value,
                timeStart.value,
                timeEnd.value,
                comment.value
            )
        )
        areaField.value = ""
        placeField.value = ""
        typePlateField.value = ""
        reasonPlateField.value = ""
        equipmentField.value = ""
        dateStart.value = ""
        dateEnd.value = ""
        timeStart.value = ""
        timeEnd.value = ""
        comment.value = ""
    }
}
