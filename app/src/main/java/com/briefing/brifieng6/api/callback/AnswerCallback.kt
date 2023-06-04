package com.briefing.brifieng6.api.callback

interface AnswerCallback {
    fun onAnswerFailure(message: String)
    fun onAnswerSuccess(result: Boolean, message: String)
}