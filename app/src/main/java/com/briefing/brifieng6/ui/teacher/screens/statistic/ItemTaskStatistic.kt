package com.briefing.brifieng6.ui.teacher.screens.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.briefing.brifieng6.ui.student.recycler.OnTaskListener
import com.briefing.brifieng6.ui.student.recycler.RecyclerViewAdapter
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.brifieng6.ui.student.viewmodel.HomeStudViewModel
import com.briefing.brifieng6.ui.student.viewmodel.HomeStudViewModelFactory
import com.briefing.test.R
import com.briefing.test.databinding.TasksStatisticBinding

class ItemTaskStatistic(
    private val group: String
) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var binding: TasksStatisticBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TasksStatisticBinding.inflate(inflater, container, false)
        recyclerView = binding!!.recyclerView
        swipeRefreshLayout = binding!!.swipeRefresh
        adapter = RecyclerViewAdapter(
            object : OnTaskListener {
                override fun onTaskClick(data: ItemTaskData?) {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, ItemStudentStatistic(group, data))
                        .addToBackStack(null)
                        .commit()
                }
            }, emptyList()
        )
        getTasks(adapter)
        recyclerView.adapter = adapter
        return binding!!.root
    }

    private fun getTasks(adapter: RecyclerViewAdapter) {
        val viewModelFactory = HomeStudViewModelFactory(group)
        val viewModel = ViewModelProvider(this, viewModelFactory)[HomeStudViewModel::class.java]
        viewModel.fetchTasks(adapter)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchTasks(adapter)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}