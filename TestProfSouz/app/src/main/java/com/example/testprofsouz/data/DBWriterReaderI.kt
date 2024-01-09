package com.example.testprofsouz.data

import com.example.testprofsouz.domain.model.DownTimeInfo
import com.example.testprofsouz.repository.DownTimeEntity

interface DBWriterReaderI {

    fun getNewDataFromUser(downTimeInfo: DownTimeInfo): Thread

    fun writeToLocalDB(downTimeEntity: DownTimeEntity)

//    fun postToServer(downTimeInfo: DownTimeInfo)

    fun getFromLocalDb(): List<DownTimeEntity>

//    fun getFromServer(): List<DownTimeInfo>
}