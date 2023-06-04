package com.briefing.brifieng6.api.domain

import android.util.Log
import com.briefing.brifieng6.api.model.LoginReceiveRemote
import com.briefing.brifieng6.api.model.LoginResponseRemote
import com.briefing.brifieng6.api.callback.LoginCallback
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class LoginController {
    private val BASE_URL = "http://192.168.0.200:8082/"
    private val client = OkHttpClient()

    fun enter(loginReceiveRemote: LoginReceiveRemote, callback: LoginCallback) {
        val json = Json.encodeToString(loginReceiveRemote)
        val mediaType = "application/json".toMediaType()
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(BASE_URL + "login")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("ПРОВАЛЬНО", "ЗАПРОС НЕ ОТПРАВИЛСЯ")
                callback.onLoginFailure("Не удалось подключиться " + request.url)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string()
                    val responseObj = Json.decodeFromString<LoginResponseRemote>(responseString.toString())
                    val teacher = responseObj.teacher
                    val token = responseObj.token_
                    callback.onLoginSuccess(teacher, token)
                } else {
                    val responseString = response.body?.string()
                    callback.onLoginFailure(responseString)
                }
            }
        })
    }

}