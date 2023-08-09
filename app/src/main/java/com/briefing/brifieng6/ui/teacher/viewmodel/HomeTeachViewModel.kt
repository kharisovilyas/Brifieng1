package com.briefing.brifieng6.ui.teacher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.briefing.brifieng6.api.domain.ItemGroupController
import com.briefing.brifieng6.ui.teacher.recycler.GroupRecyclerAdapter
import com.briefing.brifieng6.ui.student.recycler.model.ItemGroupData

class HomeTeachViewModel(private val university: String) : ViewModel() {
    private val _data = MutableLiveData<ItemGroupData>()
    val data: LiveData<ItemGroupData>
        get() = _data

    private val itemGroupController = ItemGroupController(university)
    fun fetchGroups(adapter: GroupRecyclerAdapter) {
        itemGroupController.fetchGroups(adapter)
    }
}
class HomeTeachViewModelFactory(private val university: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeTeachViewModel::class.java)) {
            return HomeTeachViewModel(university) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}