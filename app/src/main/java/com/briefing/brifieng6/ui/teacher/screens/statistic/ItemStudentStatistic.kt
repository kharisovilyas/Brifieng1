package com.briefing.brifieng6.ui.teacher.screens.statistic

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.brifieng6.ui.student.viewmodel.AnswerViewModel
import com.briefing.brifieng6.ui.teacher.recycler.OnStudentListener
import com.briefing.brifieng6.ui.teacher.recycler.StudentRecyclerAdapter
import com.briefing.brifieng6.ui.teacher.recycler.model.ItemStudentData
import com.briefing.brifieng6.ui.teacher.viewmodel.ItemStudentStatisticViewModel
import com.briefing.brifieng6.ui.teacher.viewmodel.ItemStudentStatisticViewModelFactory
import com.briefing.test.R
import com.briefing.test.databinding.StudentsStatisticBinding

class ItemStudentStatistic(
    private val group: String,
    private val dataTask: ItemTaskData?
) : Fragment() {
    private var binding: StudentsStatisticBinding? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StudentsStatisticBinding.inflate(inflater, container, false)
        recyclerView = binding!!.recyclerView
        swipeRefreshLayout = binding!!.swipeRefresh
        val viewModel = AnswerViewModel()
        adapter = StudentRecyclerAdapter(
            object : OnStudentListener {
                override fun onStudentClick(dataStudent: ItemStudentData?) {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, StudentResultsFragment(dataStudent, dataTask))
                        .addToBackStack(null)
                        .commit()
                }
            },
            emptyList(),
            viewModel,
            viewLifecycleOwner,
            dataTask!!
        )
        getUsers(adapter)
        recyclerView.adapter = adapter
        return binding!!.root
    }

    private fun getUsers(adapter: StudentRecyclerAdapter) {
        val viewModelFactory = ItemStudentStatisticViewModelFactory(group)
        val viewModel =
            ViewModelProvider(this, viewModelFactory)[ItemStudentStatisticViewModel::class.java]
        viewModel.fetchStudents(adapter)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchStudents(adapter)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getGroupFromSharedPreference(): String {
        val sharedPreferences =
            requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)
        return sharedPreferences.getString("group", "") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}