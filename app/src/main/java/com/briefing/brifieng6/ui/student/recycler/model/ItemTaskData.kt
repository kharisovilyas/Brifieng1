package com.briefing.brifieng6.ui.student.recycler.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemTaskData(
    var title: String,
    var group_: String,
    var body: String,
    var answer: String,
    var time_: Int,
    var date_: String,
    var result_: Boolean
)
