package com.briefing.brifieng6.ui.teacher.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.briefing.brifieng6.ui.student.recycler.GroupRecyclerAdapter
import com.briefing.brifieng6.ui.student.recycler.OnGroupListener
import com.briefing.brifieng6.ui.student.recycler.model.ItemGroupData
import com.briefing.brifieng6.ui.student.viewmodel.HomeStudViewModel
import com.briefing.brifieng6.ui.teacher.viewmodel.HomeTeachViewModel
import com.briefing.brifieng6.ui.teacher.viewmodel.HomeTeachViewModelFactory
import com.briefing.test.R
import com.briefing.test.databinding.HomeTeachFragmentBinding

class HomeTeachFragment : Fragment() {
    private var binding: HomeTeachFragmentBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupRecyclerAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeTeachFragmentBinding.inflate(inflater, container, false)
        recyclerView = binding!!.recyclerView
        swipeRefreshLayout = binding!!.swipeRefresh
        adapter = GroupRecyclerAdapter(
            object : OnGroupListener {
                override fun onGroupClick(data: ItemGroupData?) {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, ToTaskFragment(data))
                        .addToBackStack(null)
                        .commit()
                }
            }, emptyList()
        )
        getGroups(adapter)
        recyclerView.adapter = adapter
        return binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getGroups(adapter: GroupRecyclerAdapter) {

        //TODO:
        // сделать так, чтобы либо через SP указывалось либо через БДшку

        val group = "spbgti"
        val viewModelFactory = HomeTeachViewModelFactory(group)
        val viewModel = ViewModelProvider(this, viewModelFactory)[HomeTeachViewModel::class.java]
        viewModel.fetchGroups(adapter)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchGroups(adapter)
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