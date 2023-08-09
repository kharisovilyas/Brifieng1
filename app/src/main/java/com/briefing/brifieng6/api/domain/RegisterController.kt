package com.briefing.brifieng6.api.domain

import android.annotation.SuppressLint
import android.util.Log
import com.briefing.brifieng6.api.model.RegisterReceiveRemote
import com.briefing.brifieng6.api.callback.RegisterCallback
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.brifieng6.ui.teacher.recycler.StudentRecyclerAdapter
import com.briefing.brifieng6.ui.teacher.recycler.model.ItemStudentData
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import kotlinx.coroutines.*


class RegisterController() {
    private val BASE_URL = "http://192.168.1.145:8082/"
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

    @OptIn(DelicateCoroutinesApi::class)
    internal fun getUserdata(login: String, callback: RegisterCallback) {
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
                    client.get<RegisterReceiveRemote>(BASE_URL + "return/userdata?login=$login")
                withContext(Dispatchers.Main) {
                    callback.getRegisterCallback(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    internal fun getStudentUserdata(group: String, adapter: StudentRecyclerAdapter){
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
                    client.get<List<ItemStudentData>>(BASE_URL + "return/students/userdata?group=$group")
                withContext(Dispatchers.Main) {
                    adapter.setItemsInStart(response)
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}