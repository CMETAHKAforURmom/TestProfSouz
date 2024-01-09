package com.example.testprofsouz.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocalDBI {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(downTimeEntity: DownTimeEntity)

    @Query("SELECT * FROM downtime_table")
    fun getAll(): List<DownTimeEntity>
}