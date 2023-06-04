package com.briefing.brifieng6.ui.student.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.briefing.brifieng6.ui.student.recycler.OnTaskListener
import com.briefing.brifieng6.ui.student.recycler.GroupRecyclerAdapter
import com.briefing.brifieng6.ui.student.recycler.RecyclerViewAdapter
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.brifieng6.ui.student.viewmodel.HomeStudViewModel
import com.briefing.brifieng6.ui.student.viewmodel.HomeStudViewModelFactory
import com.briefing.test.R
import com.briefing.test.databinding.HomeStudFragmentBinding

class HomeStudFragment : Fragment(), OnTaskListener {
    private var binding: HomeStudFragmentBinding? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeStudFragmentBinding.inflate(inflater, container, false)
        recyclerView = binding!!.recyclerView
        swipeRefreshLayout = binding!!.swipeRefresh
        adapter = RecyclerViewAdapter(
            object : OnTaskListener {
                override fun onTaskClick(data: ItemTaskData?) {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, ToAnswerFragment(data))
                        .addToBackStack(null)
                        .commit()
                }
            }, emptyList()
        )
        getTasks(adapter)
        recyclerView.adapter = adapter
        return binding!!.root
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getTasks(adapter: RecyclerViewAdapter) {

        //TODO:
        // сделать так, чтобы либо через SP указывалось либо через БДшку

        val group = "test424"
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


    override fun onTaskClick(data: ItemTaskData?) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ToAnswerFragment(data))
            .addToBackStack(null)
            .commit()
    }
}