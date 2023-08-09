package com.briefing.brifieng6.api.model

import kotlinx.serialization.Serializable

@Serializable
data class AnswerReceiveRemote(
    val login: String,
    val title: String,
    val group_: String,
    val answer: String
)

@Serializable
data class AnswerResponseRemote(
    val result: Boolean,
    val details: String
)

@Serializable
data class AnswerDTO(
    val login: String,
    val title: String,
    val answer: String,
    val solved: Boolean,
    val result_: Boolean
)