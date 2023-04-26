package com.briefing.brifieng6.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.briefing.brifieng6.database.`object`.RegisterData
import com.briefing.brifieng6.database.controller.Repository

//TODO: class which communicate with activity ( or fragments!) and repository
class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    //returns the entire table
    private val registerLiveData: LiveData<List<RegisterData?>?>?
    private val repository: Repository

    //methods interface dataDao using in repository multithreading
    suspend fun insert(registerData: RegisterData?) {
        registerData?.let { repository.insert(it) }
    }

    suspend fun update(registerData: RegisterData?) {
        registerData?.let { repository.update(it) }
    }

    suspend fun delete(registerData: RegisterData?) {
        registerData?.let { repository.delete(it) }
    }

    suspend fun deleteAllData() {
        repository.deleteAllData()
    }

    //constructor
    init {
        repository = Repository(application)
        registerLiveData = repository.registerData
    }
}