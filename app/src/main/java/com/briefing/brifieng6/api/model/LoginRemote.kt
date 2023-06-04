package com.briefing.brifieng6.api.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveRemote(
    val login: String,
    val password_: String
)

@Serializable
data class LoginResponseRemote(
    val token_: String,
    val teacher: Boolean
)