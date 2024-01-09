package com.example.testprofsouz.presentation.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testprofsouz.R
import com.example.testprofsouz.presentation.viewmodel.DownTimeEquipmentEnum
import com.example.testprofsouz.presentation.viewmodel.MainScreenViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownTimeScreen(mainScreenViewModel: MainScreenViewModel) {

    Box(modifier = Modifier.fillMaxSize()){
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        CardElementLarge(
            nameCard = DownTimeEquipmentEnum.PLOT.showName,
            selected = mainScreenViewModel.areaField.value,
            model = mainScreenViewModel
        )
        CardElementLarge(
            nameCard = DownTimeEquipmentEnum.PLACE.showName,
            selected = mainScreenViewModel.placeField.value,
            model = mainScreenViewModel
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            CardElementLarge(
                nameCard = DownTimeEquipmentEnum.DATESTART.showName,
                selected = mainScreenViewModel.dateStart.value,
                model = mainScreenViewModel,
                isTwoCard =  true
            )
            CardElementLarge(nameCard = DownTimeEquipmentEnum.TIMESTART.showName, selected = mainScreenViewModel.timeStart.value, model = mainScreenViewModel)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            CardElementLarge(nameCard = DownTimeEquipmentEnum.DATEEND.showName, selected = mainScreenViewModel.dateEnd.value, model = mainScreenViewModel,
                isTwoCard = true)
            CardElementLarge(nameCard = DownTimeEquipmentEnum.TIMEEND.showName, selected = mainScreenViewModel.timeEnd.value, model = mainScreenViewModel)
        }
        CardElementLarge(
            nameCard = DownTimeEquipmentEnum.TYPE.showName,
            selected = mainScreenViewModel.typePlateField.value,
            model = mainScreenViewModel
        )
        CardElementLarge(
            nameCard = DownTimeEquipmentEnum.REASON.showName,
            selected = mainScreenViewModel.reasonPlateField.value,
            model = mainScreenViewModel
        )
        CardElementLarge(
            nameCard = DownTimeEquipmentEnum.EQUIPMENT.showName,
            selected = mainScreenViewModel.equipmentField.value,
            model = mainScreenViewModel
        )
        TextField(
            value = mainScreenViewModel.comment.value, onValueChange = { mainScreenViewModel.comment.value = it },
            placeholder = { Text(text = DownTimeEquipmentEnum.COMMENT.showName) },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.9f)
                .height(65.dp)
                .border(1.dp, Color.Black)
        )
        Button(
            onClick = {
                mainScreenViewModel.setResponseScreen()
                      },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.second_palitre_color)),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(100.dp)
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Text(
                text = "Добавить простой",
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }

            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
                contentAlignment = Alignment.Center) {
                val timer = Calendar.getInstance()
                val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    timer[Calendar.HOUR_OF_DAY] = hourOfDay
                    timer[Calendar.MINUTE] = minute
                    mainScreenViewModel.addTime(timer.timeInMillis)
                }
                val pickerTimeCompose = TimePickerDialog(
                    mainScreenViewModel.context,
                    timeListener,
                    timer[Calendar.HOUR_OF_DAY],
                    timer[Calendar.MINUTE],
                    true
                )

                val calendar = Calendar.getInstance()
                val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                    calendar[Calendar.MONTH] = month
                    calendar[Calendar.YEAR] = year

                    mainScreenViewModel.addDate(calendar.timeInMillis)
                    pickerTimeCompose.show()
                }

                val pickerCompose = DatePickerDialog(
                    mainScreenViewModel.context,
                    listener,
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                )
                mainScreenViewModel.setPickerDialog(pickerCompose, pickerTimeCompose)
        }
    }
}

@Composable
fun CardElementLarge(nameCard: String, selected: String, isTwoCard: Boolean = false, model: MainScreenViewModel? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth(if (isTwoCard) 0.47f else 0.9f)
            .padding(top = 15.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(6.dp))
            .background(colorResource(id = R.color.white))
            .clickable {
                model?.selectDate(nameCard)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                Text(
                    text = nameCard,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(
                        id = R.color.small_text_color
                    )
                )
                if (selected.isNotEmpty())
                    Text(text = selected, style = MaterialTheme.typography.titleLarge)
            }
            Icon(
                painter = painterResource(id = R.drawable.arrow_select),
                tint = colorResource(id = R.color.small_text_color),
                contentDescription = "Button for select current element",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}