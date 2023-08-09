package com.briefing.brifieng6.ui.teacher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.briefing.brifieng6.api.callback.AnswerCallback
import com.briefing.brifieng6.api.callback.TaskCallback
import com.briefing.brifieng6.api.domain.TaskController
import com.briefing.brifieng6.api.model.TaskReceiveRemote

class ToTaskViewModel: ViewModel() {

    private val taskController = TaskController()
    private val _dataTask = MutableLiveData<TaskReceiveRemote>()
    val dataTask: LiveData<TaskReceiveRemote>
        get() = _dataTask
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun sendTask(taskReceiveRemote: TaskReceiveRemote) {
        taskController.sendTask(taskReceiveRemote, object : TaskCallback {
            override fun onGetTaskSuccess(taskReceiveRemote: TaskReceiveRemote) {
                TODO("Not yet implemented")
            }

            override fun onGetTaskFailure(message: String) {
                TODO("Not yet implemented")
            }

            override fun onSendTaskFailure(message: String) {
                _errorMessage.postValue(message)
            }

            override fun onSendTaskSuccess(message: String) {
                _message.postValue(message)
            }


        })
    }

}