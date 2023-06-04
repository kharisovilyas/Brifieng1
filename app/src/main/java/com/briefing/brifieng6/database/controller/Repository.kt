package com.briefing.brifieng6.database.controller

import android.app.Application
import androidx.lifecycle.LiveData
import com.briefing.brifieng6.database.AppDatabase
import com.briefing.brifieng6.database.`object`.UserData
import com.briefing.brifieng6.database.dao.DataDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(application: Application) {

    private val dataDao: DataDao
    val registerData: LiveData<List<UserData>>?

    init {
        val appDataBase = AppDatabase.getInstance(application)
        dataDao = appDataBase.dataDao()
        registerData = dataDao.allLiveData
    }

    suspend fun insert(registerData: UserData) {
        withContext(Dispatchers.IO) {
            dataDao.insert(registerData)
        }
    }

    suspend fun update(registerData: UserData) {
        withContext(Dispatchers.IO) {
            dataDao.update(registerData)
        }
    }

    suspend fun delete(registerData: UserData) {
        withContext(Dispatchers.IO) {
            dataDao.delete(registerData)
        }
    }

    suspend fun deleteAllData() {
        withContext(Dispatchers.IO) {
            dataDao.deleteAllData()
        }
    }
/*
    fun getRegisterData(): LiveData<List<RegisterData>>? {
        return registerData
    }*/
}
