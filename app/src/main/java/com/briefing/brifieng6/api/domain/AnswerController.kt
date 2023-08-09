package com.briefing.brifieng6.api.domain

import android.util.Log
import com.briefing.brifieng6.api.callback.AnswerCallback
import com.briefing.brifieng6.api.callback.RegisterCallback
import com.briefing.brifieng6.api.model.*
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class AnswerController {
    private val BASE_URL = "http://192.168.1.145:8082/"
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

                    val responseString = response.body?.string()
                    if (responseString != null) {
                        callback.onAnswerFailure(responseString)
                    }else{
                        callback.onAnswerFailure("Что то пошло не так!")
                    }
                }
            }
        })
    }
    @OptIn(DelicateCoroutinesApi::class)
    internal fun getAnswerData(login: String, title: String, callback: AnswerCallback) {
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
                    client.get<AnswerDTO>(BASE_URL + "return/userdata?login=$login&title=$title")
                withContext(Dispatchers.Main) {
                    callback.getAnswerCallback(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}