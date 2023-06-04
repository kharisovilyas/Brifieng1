package com.briefing.brifieng6.api.model

import kotlinx.serialization.Serializable
@Serializable
data class RegisterReceiveRemote(
    val login: String,
    val password_: String,
    val email: String,
    val name_: String,
    val surname: String,
    val group_ : String,
    val university: String,
    val teacher: Boolean
)
@Serializable
data class RegisterResponseRemote(
    val token_: String
)