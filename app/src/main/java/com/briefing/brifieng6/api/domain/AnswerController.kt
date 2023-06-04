package com.briefing.brifieng6.api.domain

import android.util.Log
import com.briefing.brifieng6.api.callback.AnswerCallback
import com.briefing.brifieng6.api.model.AnswerReceiveRemote
import com.briefing.brifieng6.api.model.AnswerResponseRemote
import com.briefing.brifieng6.api.model.LoginResponseRemote
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class AnswerController {
    private val BASE_URL = "http://192.168.0.200:8082/"
    private val client = OkHttpClient()
    fun sendAnswer(answerReceiveRemote: AnswerReceiveRemote, callback: AnswerCallback) {
        val json = Json.encodeToString(answerReceiveRemote)
        val mediaType = "application/json".toMediaType()
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(BASE_URL + "student/result")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string()
                    val responseObj = Json.decodeFromString<AnswerResponseRemote>(responseString.toString())
                    val result = responseObj.result
                    val details = responseObj.details
                    callback.onAnswerSuccess(result, details)
                } else {
                    Log.d("ПРОВАЛЬНО", "соденинение не установленно")
                    callback.onAnswerFailure(response.message)
                }
            }
        })
    }

}