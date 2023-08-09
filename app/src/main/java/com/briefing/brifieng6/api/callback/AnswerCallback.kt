package com.briefing.brifieng6.api.callback

import com.briefing.brifieng6.api.model.AnswerDTO
import com.briefing.brifieng6.api.model.RegisterReceiveRemote

interface AnswerCallback {
    fun onAnswerFailure(message: String)
    fun onAnswerSuccess(result: Boolean, message: String)
    fun getAnswerCallback(answerDTO: AnswerDTO)
}