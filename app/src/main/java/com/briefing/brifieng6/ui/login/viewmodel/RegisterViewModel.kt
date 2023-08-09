package com.briefing.brifieng6.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.briefing.brifieng6.api.model.RegisterReceiveRemote
import com.briefing.brifieng6.api.callback.RegisterCallback
import com.briefing.brifieng6.api.domain.RegisterController

class RegisterViewModel : ViewModel() {

    private val _isSuccessfulRegister = MutableLiveData<Boolean>()
    val isSuccessfulRegister: LiveData<Boolean>
        get() = _isSuccessfulRegister
    private val _errorMessage = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _message = MutableLiveData<String>()

    val message: LiveData<String>
        get() = _message

    private val registerController = RegisterController()

    private val _userdata = MutableLiveData<RegisterReceiveRemote>()
    val userdata: LiveData<RegisterReceiveRemote>
        get() = _userdata

    fun insertUser(registerData: RegisterReceiveRemote) {
        registerController.register(registerData, object : RegisterCallback {
            override fun onRegisterSuccess(message: String) {
                _isSuccessfulRegister.postValue(true)
                _message.postValue(message)
            }

            override fun onRegisterFailure(message: String?) {
                if (message == null) {
                    _errorMessage.postValue("Что то пошло не так!")
                }
                _errorMessage.postValue(message!!)
            }

            override fun getRegisterCallback(userdata: RegisterReceiveRemote) {
                TODO("Not yet implemented")
            }
        })
    }

    fun fetchUserData(login: String){
        registerController.getUserdata(login = login, object:RegisterCallback {
            override fun onRegisterSuccess(message: String) {
                TODO("Not yet implemented")
            }

            override fun onRegisterFailure(message: String?) {
                TODO("Not yet implemented")
            }

            override fun getRegisterCallback(userdata: RegisterReceiveRemote) {
                _userdata.postValue(userdata)
            }

        })
    }
}
