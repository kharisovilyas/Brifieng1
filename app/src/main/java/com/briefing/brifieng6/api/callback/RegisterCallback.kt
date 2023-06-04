package com.briefing.brifieng6.api.callback

interface RegisterCallback {
    fun onRegisterSuccess(message: String)
    fun onRegisterFailure(message: String?)
}