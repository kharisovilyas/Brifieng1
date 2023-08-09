package com.briefing.brifieng6.api.callback

import com.briefing.brifieng6.api.model.RegisterReceiveRemote

interface RegisterCallback {
    fun onRegisterSuccess(message: String)
    fun onRegisterFailure(message: String?)
    fun getRegisterCallback(userdata: RegisterReceiveRemote)
}