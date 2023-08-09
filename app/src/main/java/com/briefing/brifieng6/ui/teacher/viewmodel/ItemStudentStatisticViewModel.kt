package com.briefing.brifieng6.ui.teacher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.briefing.brifieng6.api.domain.ItemGroupController
import com.briefing.brifieng6.api.domain.RegisterController
import com.briefing.brifieng6.api.model.AnswerReceiveRemote
import com.briefing.brifieng6.ui.student.recycler.model.ItemGroupData
import com.briefing.brifieng6.ui.teacher.recycler.GroupRecyclerAdapter
import com.briefing.brifieng6.ui.teacher.recycler.StudentRecyclerAdapter

class ItemStudentStatisticViewModel(private val group: String) : ViewModel() {
    private val _data = MutableLiveData<ItemGroupData>()
    val data: LiveData<ItemGroupData>
        get() = _data

    private val itemStudentController = RegisterController()
    fun fetchStudents(adapter: StudentRecyclerAdapter) {
        itemStudentController.getStudentUserdata(
            group = group,
            adapter
        )
    }

    private val _dataResult = MutableLiveData<AnswerReceiveRemote>()
    val dataResult: LiveData<AnswerReceiveRemote>
        get() = _dataResult
}

class ItemStudentStatisticViewModelFactory(private val group: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemStudentStatisticViewModel::class.java)) {
            return ItemStudentStatisticViewModel(group) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}