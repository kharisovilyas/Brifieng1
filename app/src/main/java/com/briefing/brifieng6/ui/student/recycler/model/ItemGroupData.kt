package com.briefing.brifieng6.ui.student.recycler.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemGroupData(
    val group_: String,
    val numberOfStudent: Int,
    val university: String
)