package com.briefing.brifieng6.server.domain

import android.util.Log
import com.briefing.brifieng6.server.`object`.RegisterReceiveRemote
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class RegisterController {
    private val BASE_URL = "http://192.168.0.199:8082/"
    private val client = OkHttpClient()

    fun register(login: String, password: String, email: String, name: String, surname: String, isTeacher: Boolean) {
        val data = RegisterReceiveRemote(login, password, email, name, surname, isTeacher)
        val json = Json.encodeToString(data)

        val mediaType = "application/json".toMediaType()
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(BASE_URL + "register")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("УСПЕШНО", "соденинение установленно")
                    // Обработка успешного ответа
                } else {
                    // Обработка ошибки
                    Log.d("ПРОВАЛЬНО", "соденинение не установленно")
                }
            }
        })
    }
}