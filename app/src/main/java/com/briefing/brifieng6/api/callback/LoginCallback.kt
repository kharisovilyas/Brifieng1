package com.briefing.brifieng6.api.callback

interface LoginCallback {
    fun onLoginSuccess(teacher: Boolean, token: String, myMessage: String?)
    fun onLoginFailure(myMessage: String?)
}