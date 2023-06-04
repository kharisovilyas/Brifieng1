package com.briefing.brifieng6.ui.student.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.briefing.brifieng6.api.model.AnswerReceiveRemote
import com.briefing.brifieng6.ui.login.useralert.DialogGreetings
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.brifieng6.ui.student.viewmodel.AnswerViewModel
import com.briefing.test.databinding.FragmentToAnswerBinding
import com.google.android.material.snackbar.Snackbar

class ToAnswerFragment(
    private val itemTaskData: ItemTaskData?
) : Fragment() {
    private var binding: FragmentToAnswerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val title: String = itemTaskData!!.title
        binding = FragmentToAnswerBinding.inflate(inflater, container, false)
        binding!!.title.text = title
        setTaskData()
        binding!!.sendAnswer.setOnClickListener{
            sendAnswerData(title)
        }
        return binding!!.root
    }

    @SuppressLint("SetTextI18n")
    private fun setTaskData() {
        binding!!.time.text = "Осталось: " + getCorrectTime(itemTaskData!!.time_)
        binding!!.date.text = "Срок до: " + itemTaskData.date_
        binding!!.questionTextView.text = itemTaskData.body
    }

    //никому не нужный get запрос на сервер..
    /*@SuppressLint("SetTextI18n")
    private fun getTaskDataByTitle(title: String) {
        val viewModel = ViewModelProvider(this)[AnswerViewModel::class.java]
        viewModel.fetchTask(title)
        viewModel.dataTask.observe(viewLifecycleOwner, Observer { taskReceiveRemote ->
            binding!!.time.text = "Осталось: " + getCorrectTime(taskReceiveRemote.time_)
            binding!!.date.text = "Срок до: " + taskReceiveRemote.date_
            binding!!.questionTextView.text = taskReceiveRemote.body
        })
    }*/

    private fun sendAnswerData(title: String) {
        val answerViewModel = AnswerViewModel()
        val answer = binding!!.answerInputEditText.text.toString()
        answerViewModel.sendAnswer(
            AnswerReceiveRemote(
                login = "test2",
                title = title,
                group_ = "test424",
                answer = answer
            )
        )
        answerViewModel.details.observe(viewLifecycleOwner){
            informUser(answerViewModel.result.value, it)
        }
    }

    private fun informUser(it: Boolean?, details: String) {
        if(it == null){
            val snack = Snackbar
                .make(binding!!.toAnswContainer, "Ошибка !", Snackbar.LENGTH_SHORT)
            snack.setActionTextColor(Color.RED)
            snack.show()
        }
        val dialogFragment = DialogGreetings(it == true, details)
        Log.d("ЗДЕСЬ!", details)
        dialogFragment.show(parentFragmentManager, "dialog_greetings")
    }

    private fun getCorrectTime(time_: Int): String {
        val minutes = time_ / 60
        val seconds = time_ % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}