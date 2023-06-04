package com.briefing.brifieng6.ui.teacher.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.api.model.TaskReceiveRemote
import com.briefing.brifieng6.ui.student.recycler.model.ItemGroupData
import com.briefing.brifieng6.ui.teacher.viewmodel.ToTaskViewModel
import com.briefing.test.databinding.ToTaskFragmentBinding

class ToTaskFragment(private val data: ItemGroupData?): Fragment() {

    private var binding: ToTaskFragmentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ToTaskFragmentBinding.inflate(inflater, container, false)
        binding!!.forGroupText.text = "Задача группе: " + data!!.group_
        binding!!.exit.setOnClickListener {
            requireActivity()
                .supportFragmentManager.popBackStack()
        }
        binding!!.sendTask.setOnClickListener {
            sendTaskData()
        }
        return binding!!.root
    }

    private fun sendTaskData() {
        val toTaskViewModel = ToTaskViewModel()
        val title = binding!!.titleInputEditText.text.toString()
        val time = binding!!.timeInputEditText.text.toString()
        val date = binding!!.dateInputEditText.text.toString()
        val question = binding!!.questionInputEditText.text.toString()
        val correctAnswer = binding!!.answerCorrectInputEditText.text.toString()
        toTaskViewModel.sendTask(
            TaskReceiveRemote(
                title = title,
                group_ = data!!.group_,
                answer = correctAnswer,
                date_ = date,
                time_ = time.toInt(),
                body = question,
                result_ = false
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}