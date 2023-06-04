package com.briefing.brifieng6.api.domain

import android.annotation.SuppressLint
import com.briefing.brifieng6.ui.student.recycler.GroupRecyclerAdapter
import com.briefing.brifieng6.ui.student.recycler.RecyclerViewAdapter
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.*

class ItemTaskController(private val group: String) {


    private val BASE_URL = "http://192.168.0.200:8082/"

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    internal fun fetchTasks(adapter: RecyclerViewAdapter) {
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
                val response = client.get<List<ItemTaskData>>(BASE_URL + "return/all/tasks?group=$group")
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


    /*

    @SuppressLint("NotifyDataSetChanged")
    fun fetchTasks(){
        var tasks = emptyList<ItemTaskData>()
        GlobalScope.launch {
            val client = HttpClient {
                install(JsonFeature)
            }
            tasks = client.get<List<ItemTaskData>> {
                url(BASE_URL + "return/all/tasks?group=test424")
            }
            adapter.data = tasks
            adapter.notifyDataSetChanged()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    internal fun fetchTasks() {
        val request = Request.Builder()
            .url(BASE_URL + "register")
            .get()
            .build()
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
                val response = client.get<List<ItemTaskData>>(BASE_URL + "return/all/tasks?group=test424")
                withContext(Dispatchers.Main) {
                    adapter.data = response
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    suspend fun fetchTasks(){
        val client = HttpClient()
        client.use { client ->
            val response: HttpResponse = client.get("http://localhost/return/all/tasks?group=test424")
            val json = response.readText()
            val taskData = Json.decodeFromString<List<ItemTaskData>>(json)
            withContext(Dispatchers.Main) {
                adapter.data = taskData
                adapter.notifyDataSetChanged()
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    internal fun fetchTasks() {
        val client = HttpClient(CIO)
        // создаем HttpClient
        val url = URLBuilder().apply {
            takeFrom(BASE_URL)
            path("return/all/tasks?group=test424")
        }
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            headers.append("Content-Type", "application/json")
            url(url.build())
        }

        // делаем запрос на получение заданий
        GlobalScope.launch {
            try {
                val response = client.request<List<ItemTaskData>>(request) // отправляем запрос и получаем ответ
                withContext(Dispatchers.Main) {
                    // заполняем RecyclerView данными из ответа сервера
                    adapter.data = response
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                // обработка ошибки
            }
        }
    }
}

     */