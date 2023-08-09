package com.briefing.brifieng6.ui.teacher.recycler.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemStudentData (
    val login: String,
    val password_: String,
    val email: String,
    val name_: String,
    val surname: String,
    val university: String,
    val group_: String,
    val teacher: Boolean
)