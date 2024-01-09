package com.example.testprofsouz.data

import com.example.testprofsouz.domain.model.DownTimeInfo
import com.example.testprofsouz.repository.AppDatabase
import com.example.testprofsouz.repository.DownTimeEntity
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.concurrent.thread

class DBWriterReader @Inject constructor(private val appDatabase: AppDatabase) : DBWriterReaderI{

    override fun getNewDataFromUser(downTimeInfo: DownTimeInfo) = runBlocking {
        thread {
            writeToLocalDB(downTimeInfo.transformToEntity())
        }
    }

    override fun writeToLocalDB(downTimeEntity: DownTimeEntity) {
        appDatabase.localDBDao().insert(downTimeEntity)
    }

    override fun getFromLocalDb(): List<DownTimeEntity> {
        return appDatabase.localDBDao().getAll()
    }

}