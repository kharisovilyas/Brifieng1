package com.briefing.brifieng6.api.callback

interface LoginCallback {
    fun onLoginSuccess(teacher: Boolean, token: String)
    fun onLoginFailure(myMessage: String?)
}