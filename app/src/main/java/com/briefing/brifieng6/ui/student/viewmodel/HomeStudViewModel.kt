package com.briefing.brifieng6.ui.student.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.briefing.brifieng6.api.domain.ItemTaskController
import com.briefing.brifieng6.ui.student.recycler.RecyclerViewAdapter
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData

class HomeStudViewModel(private val university: String) : ViewModel(){
    private val _data = MutableLiveData<ItemTaskData>()
    val data: LiveData<ItemTaskData>
        get() = _data

    private val itemTaskController = ItemTaskController(university)
    fun fetchTasks(adapter: RecyclerViewAdapter) {
        itemTaskController.fetchTasks(adapter)
    }
}

class HomeStudViewModelFactory(private val group: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeStudViewModel::class.java)) {
            return HomeStudViewModel(group) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}