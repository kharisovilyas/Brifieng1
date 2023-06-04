package com.briefing.brifieng6.api.callback

import com.briefing.brifieng6.api.model.AnswerReceiveRemote
import com.briefing.brifieng6.api.model.TaskReceiveRemote
import com.briefing.brifieng6.api.model.TaskResponseRemote

interface TaskCallback {
    fun onGetTaskSuccess(taskReceiveRemote: TaskReceiveRemote)
    fun onGetTaskFailure(message: String)
    fun onSendTaskFailure(message: String)
    fun onSendTaskSuccess(message: String)
}