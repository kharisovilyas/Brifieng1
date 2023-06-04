package com.briefing.brifieng6.api.domain

import android.annotation.SuppressLint
import com.briefing.brifieng6.ui.student.recycler.GroupRecyclerAdapter
import com.briefing.brifieng6.ui.student.recycler.RecyclerViewAdapter
import com.briefing.brifieng6.ui.student.recycler.model.ItemGroupData
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json

class ItemGroupController(private val university: String) {
    private val BASE_URL = "http://192.168.0.200:8082/"

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    internal fun fetchGroups(adapter: GroupRecyclerAdapter) {
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
                val response = client.get<List<ItemGroupData>>(BASE_URL + "return/groups?university=$university")
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