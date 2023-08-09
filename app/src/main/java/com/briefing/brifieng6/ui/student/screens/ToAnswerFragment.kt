package com.briefing.brifieng6.ui.student.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.api.model.AnswerReceiveRemote
import com.briefing.brifieng6.ui.login.useralert.InformDialog
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.brifieng6.ui.student.viewmodel.AnswerViewModel
import com.briefing.test.R
import com.briefing.test.databinding.FragmentToAnswerBinding
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

class ToAnswerFragment(
    private val itemTaskData: ItemTaskData?
) : Fragment() {
    private var binding: FragmentToAnswerBinding? = null
    private var isRunning = false // Флаг, указывающий, выполняется ли уже задача

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = AnswerViewModel()
        val title: String = itemTaskData!!.title
        binding = FragmentToAnswerBinding.inflate(inflater, container, false)
        startImplTask(viewModel)
        binding!!.title.text = title
        setTaskData()
        viewModel.timeIsUp.observe(viewLifecycleOwner) {
            if (it) {
                binding!!.sendAnswer.isEnabled = false
                sendAnswerData(title, viewModel)
            }
        }
        binding!!.sendAnswer.setOnClickListener {
            sendAnswerData(title, viewModel)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            informUser(false, it)
        }
        return binding!!.root
    }


    private fun startImplTask(viewModel: AnswerViewModel) {
        if (!isRunning) {
            isRunning = true
            setTime(itemTaskData!!.time_, viewModel)
        }
    }

    private fun setTime(time: Int, viewModel: AnswerViewModel) {
        val countDownLatch = CountDownLatch(time)

        thread {
            for (i in time downTo 1) {
                // Уменьшаем значение времени на 1 секунду
                val currentTime = i - 1

                // Обновляем значение времени в UI
                activity?.runOnUiThread {
                    binding?.time?.text = formatTime(currentTime)
                }

                // Ожидаем 1 секунду перед уменьшением значения времени
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                // Уменьшаем счетчик CountDownLatch
                countDownLatch.countDown()
            }

            isRunning = false // По окончании задачи сбрасываем флаг выполнения
            viewModel.setTimeIsOut()

        }
    }

    private fun formatTime(time: Int): String {
        val minutes = time / 60
        val seconds = time % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    @SuppressLint("SetTextI18n")
    private fun setTaskData() {
        binding!!.time.text = "Осталось: " + getCorrectTime(itemTaskData!!.time_)
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

    private fun sendAnswerData(title: String, answerViewModel: AnswerViewModel) {
        val answer = binding!!.answerInputEditText.text.toString()
        answerViewModel.sendAnswer(
            AnswerReceiveRemote(
                login = getLoginFromSharedPreference(),
                title = title,
                group_ = getGroupFromSharedPreference(),
                answer = answer
            )
        )
        answerViewModel.details.observe(viewLifecycleOwner) {
            informUser(answerViewModel.result.value, it)
        }
    }

    private fun getGroupFromSharedPreference(): String {
        val sharedPreferences =
            requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)
        return sharedPreferences.getString("group", "") ?: ""
    }

    private fun getLoginFromSharedPreference(): String {
        val sharedPreferences =
            requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)
        return sharedPreferences.getString("login", "") ?: ""
    }

    private fun informUser(it: Boolean?, details: String) {
        if (it == null) {
            val snack = Snackbar
                .make(binding!!.toAnswContainer, "Ошибка !", Snackbar.LENGTH_SHORT)
            snack.setActionTextColor(Color.RED)
            snack.show()
        }
        val dialogFragment: InformDialog = if (it == true) {
            InformDialog("Верно!", details, R.drawable.correct)

        } else {
            InformDialog("Неверно!", details, R.drawable.incorrect)

        }
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