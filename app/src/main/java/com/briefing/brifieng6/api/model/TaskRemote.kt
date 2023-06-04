package com.briefing.brifieng6.api.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskReceiveRemote(
    val title: String,
    val group_: String,
    val body: String,
    val answer: String,
    val time_: Int,
    val date_: String,
    val result_: Boolean
)

@Serializable
data class TaskResponseRemote(
    val result: Boolean,
    val details: String
)
