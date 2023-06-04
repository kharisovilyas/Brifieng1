package com.briefing.brifieng6.api.domain

import android.util.Log
import com.briefing.brifieng6.api.model.RegisterReceiveRemote
import com.briefing.brifieng6.api.callback.RegisterCallback
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class RegisterController() {
    private val BASE_URL = "http://192.168.0.200:8082/"
    private val client = OkHttpClient()
    fun register(registerReceiveRemote: RegisterReceiveRemote, callback: RegisterCallback) {
        val json = Json.encodeToString(registerReceiveRemote)
        val mediaType = "application/json".toMediaType()
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(BASE_URL + "register")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback.onRegisterFailure("Не удалось подключиться к " + request.url)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("УСПЕШНО", "соденинение установленно")
                    callback.onRegisterSuccess("Успешная регистрация!\nДобро пожаловать " + registerReceiveRemote.name_ + "!")
                } else {
                    Log.d("ПРОВАЛЬНО", "соденинение не установленно")
                    val responseString = response.body?.string()
                    callback.onRegisterFailure(responseString)
                }
            }
        })
    }
}