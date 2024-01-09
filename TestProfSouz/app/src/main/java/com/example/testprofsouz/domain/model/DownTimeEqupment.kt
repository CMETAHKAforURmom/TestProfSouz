package com.example.testprofsouz.domain.model

import com.example.testprofsouz.repository.DownTimeEntity
import java.util.UUID

data class DownTimeInfo(
    val areaField: String,
    val placeField: String,
    val typePlateField: String,
    val reasonPlateField: String,
    val equipmentField: String,
    val dateStart: String,
    val dateEnd: String,
    val timeStart: String,
    val timeEnd: String,
    val comment: String
){
    fun transformToEntity(): DownTimeEntity {
        return DownTimeEntity(UUID.randomUUID(), areaField, placeField, typePlateField, reasonPlateField, equipmentField, dateStart, dateEnd, timeStart, timeEnd, comment)
    }
}
