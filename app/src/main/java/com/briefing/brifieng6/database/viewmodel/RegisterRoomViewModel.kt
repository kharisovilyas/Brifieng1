package com.briefing.brifieng6.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.briefing.brifieng6.database.`object`.UserData
import com.briefing.brifieng6.database.controller.Repository

//TODO: class which communicate with activity ( or fragments!) and repository
class RegisterRoomViewModel(application: Application) : AndroidViewModel(application) {
    //returns the entire table
    private val registerLiveData: LiveData<List<UserData>>?
    private val repository: Repository

    //methods interface dataDao using in repository multithreading
    suspend fun insert(registerData: UserData?) {
        registerData?.let { repository.insert(it) }
    }

    suspend fun update(registerData: UserData?) {
        registerData?.let { repository.update(it) }
    }

    suspend fun delete(registerData: UserData?) {
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