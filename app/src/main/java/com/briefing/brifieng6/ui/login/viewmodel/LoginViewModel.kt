package com.briefing.brifieng6.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.briefing.brifieng6.api.model.LoginReceiveRemote
import com.briefing.brifieng6.api.callback.LoginCallback
import com.briefing.brifieng6.api.domain.LoginController

class LoginViewModel : ViewModel() {
    private val _isSuccessfulRegister = MutableLiveData<Boolean>()
    private val _token = MutableLiveData<String>()
    private val _teacher = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()
    private val _message = MutableLiveData<String>()
    val isSuccessfulLogin: LiveData<Boolean>
        get() = _isSuccessfulRegister
    val token: LiveData<String>
        get() = _token
    val teacher: LiveData<Boolean>
        get() = _teacher
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val message: LiveData<String>
        get() = _message

    private val loginController = LoginController()

    fun enterUser(loginData: LoginReceiveRemote) {
        loginController.enter(loginData, object : LoginCallback {
            override fun onLoginSuccess(teacher: Boolean, token: String, myMessage: String?) {
                _token.postValue(token)
                _teacher.postValue(teacher)
                _isSuccessfulRegister.postValue(true)
                _message.postValue(myMessage.toString())
            }

            override fun onLoginFailure(errorMessage: String?) {
                if (errorMessage == null) {
                    _errorMessage.postValue("Что то пошло не так!")
                } else {
                    _errorMessage.postValue(errorMessage.toString())
                }
                _isSuccessfulRegister.postValue(false)
            }
        })
    }

}