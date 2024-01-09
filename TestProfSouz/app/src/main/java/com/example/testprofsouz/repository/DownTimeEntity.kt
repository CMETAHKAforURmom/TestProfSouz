package com.example.testprofsouz.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "downtime_table")
data class DownTimeEntity(
    @PrimaryKey val uid: UUID,
    @ColumnInfo(name = "areaField") val areaField: String,
    @ColumnInfo(name = "placeField") val placeField: String,
    @ColumnInfo(name = "typePlateField") val typePlateField: String,
    @ColumnInfo(name = "reasonPlateField") val reasonPlateField: String,
    @ColumnInfo(name = "equipmentField") val areaequipmentFieldField: String,
    @ColumnInfo(name = "dateStart") val dateStart: String,
    @ColumnInfo(name = "dateEnd") val dateEnd: String,
    @ColumnInfo(name = "timeStart") val timeStart: String,
    @ColumnInfo(name = "timeEnd") val timeEnd: String,
    @ColumnInfo(name = "comment") val comment: String
)