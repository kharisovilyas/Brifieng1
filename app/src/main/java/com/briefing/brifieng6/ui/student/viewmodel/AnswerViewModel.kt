package com.briefing.brifieng6.ui.student.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.briefing.brifieng6.api.callback.AnswerCallback
import com.briefing.brifieng6.api.callback.RegisterCallback
import com.briefing.brifieng6.api.callback.TaskCallback
import com.briefing.brifieng6.api.domain.AnswerController
import com.briefing.brifieng6.api.domain.TaskController
import com.briefing.brifieng6.api.model.AnswerDTO
import com.briefing.brifieng6.api.model.AnswerReceiveRemote
import com.briefing.brifieng6.api.model.RegisterReceiveRemote
import com.briefing.brifieng6.api.model.TaskReceiveRemote

class AnswerViewModel() : ViewModel() {
    private val _result = MutableLiveData<Boolean>()
    val result: LiveData<Boolean>
        get() = _result
    private val answerController = AnswerController()
    private val taskController = TaskController()
    private val _dataTask = MutableLiveData<TaskReceiveRemote>()
    val dataTask: LiveData<TaskReceiveRemote>
        get() = _dataTask
    private val _dataAnswer = MutableLiveData<AnswerDTO>()
    val dataAnswer: LiveData<AnswerDTO>
        get() = _dataAnswer
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _details = MutableLiveData<String>()
    val details: LiveData<String>
        get() = _details
    private val _timeIsUp = MutableLiveData<Boolean>()
    val timeIsUp: LiveData<Boolean>
        get() = _timeIsUp

    fun fetchTask(titleOfTask: String) {
        taskController.fetchTask(titleOfTask, object : TaskCallback {
            override fun onGetTaskSuccess(taskReceiveRemote: TaskReceiveRemote) {
                _dataTask.postValue(taskReceiveRemote)
            }

            override fun onGetTaskFailure(message: String) {
                _errorMessage.postValue(message)
            }

            override fun onSendTaskFailure(message: String) {
                TODO("Not yet implemented")
            }

            override fun onSendTaskSuccess(message: String) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchAnswer(answerController: AnswerController, login: String, title: String){

        answerController.getAnswerData(login = login, title = title, object: AnswerCallback {
            override fun onAnswerFailure(message: String) {
                TODO("Not yet implemented")
            }

            override fun onAnswerSuccess(result: Boolean, message: String) {
                TODO("Not yet implemented")
            }

            override fun getAnswerCallback(answerDTO: AnswerDTO) {
                _dataAnswer.postValue(answerDTO)
            }

        })
    }

    fun setTimeIsOut(){
        _timeIsUp.postValue(true)
    }

    fun sendAnswer(answerReceiveRemote: AnswerReceiveRemote) {
        answerController.sendAnswer(answerReceiveRemote, object : AnswerCallback {
            override fun onAnswerFailure(message: String) {
                _errorMessage.postValue(message)
            }

            override fun onAnswerSuccess(result: Boolean, message: String) {
                _result.postValue(result)
                _details.postValue(message)
            }

            override fun getAnswerCallback(answerDTO: AnswerDTO) {
                TODO("Not yet implemented")
            }

        })
    }

}
