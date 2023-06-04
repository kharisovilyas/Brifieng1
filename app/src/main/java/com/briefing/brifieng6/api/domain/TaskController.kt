package com.briefing.brifieng6.api.domain

import android.annotation.SuppressLint
import android.util.Log
import com.briefing.brifieng6.api.callback.AnswerCallback
import com.briefing.brifieng6.api.callback.TaskCallback
import com.briefing.brifieng6.api.model.TaskReceiveRemote

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class TaskController {
    private val BASE_URL = "http://192.168.0.200:8082/"
    private val client = OkHttpClient()

    fun sendTask(taskReceiveRemote: TaskReceiveRemote, callback: TaskCallback) {
        val json = Json.encodeToString(taskReceiveRemote)
        val mediaType = "application/json".toMediaType()
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("$BASE_URL/send/task")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback.onSendTaskFailure("Не удалось подключиться к " + request.url)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("УСПЕШНО", "соденинение установленно")
                    callback.onSendTaskSuccess("Успешная регистрация!\nДобро пожаловать ")
                } else {
                    Log.d("ПРОВАЛЬНО", "соденинение не установленно")
                    val responseString = response.body?.string()
                    callback.onSendTaskFailure(responseString.toString())
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchTask(titleOfTask: String, callback: TaskCallback) {
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response =
                    client.get<TaskReceiveRemote>(BASE_URL + "return/task?title=$titleOfTask")
                withContext(Dispatchers.Main) {
                    callback.onGetTaskSuccess(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback.onGetTaskFailure("Task not found")
            }
        }
    }

}