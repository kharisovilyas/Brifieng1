package com.briefing.brifieng6.ui.teacher.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.api.model.TaskReceiveRemote
import com.briefing.brifieng6.ui.login.useralert.InformDialog
import com.briefing.brifieng6.ui.student.recycler.model.ItemGroupData
import com.briefing.brifieng6.ui.teacher.viewmodel.ToTaskViewModel
import com.briefing.test.R
import com.briefing.test.databinding.ToTaskFragmentBinding
import java.util.*

class ToTaskFragment(private val data: ItemGroupData?) : Fragment() {

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
        val question = binding!!.questionInputEditText.text.toString()
        val correctAnswer = binding!!.answerCorrectInputEditText.text.toString()
        val seconds = getCorrectSecondsTime()
        val date = getCorrectDate()
        var hasEmptyFields = false
        if (title.isEmpty()) {
            binding!!.titleInputLayout.error = "Заполните поле"
            hasEmptyFields = true
            println("Пустой заголовок")
        }

        if (question.isEmpty()) {
            binding!!.questionInputEditText.error = "Заполните поле"
            hasEmptyFields = true
            println("Пустой вопрос")
        }

        if (correctAnswer.isEmpty()) {
            binding!!.answerCorrectInputLayout.error = "Заполните поле"
            hasEmptyFields = true

            println("Пустой ответ")
        }

        // Если есть пустые поля, выполните необходимые действия
        if (hasEmptyFields) {
            informUser(
                "Пустые поля!",
                "Заполните необходимые поля",
                R.drawable.incorrect
            )
        } else {

            toTaskViewModel.sendTask(
                TaskReceiveRemote(
                    title = title,
                    group_ = data!!.group_,
                    answer = correctAnswer,
                    date_ = date,
                    time_ = seconds,
                    body = question,
                    result_ = false
                )
            )
        }
        toTaskViewModel.message.observe(viewLifecycleOwner){
            informUser("Задача успешно отправленна", it, R.drawable.correct)
        }
        toTaskViewModel.errorMessage.observe(viewLifecycleOwner){
            informUser("Внимание!", it, R.drawable.incorrect)
        }
    }

    private fun getCorrectDate(): String {

        val currentDate = Calendar.getInstance()
        val myDate = binding!!.inputDate
        val selectedYear = myDate.year
        val selectedMonth = myDate.month
        val selectedDayOfMonth = myDate.dayOfMonth

        val selectedDate = Calendar.getInstance()
        selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
        println("$selectedYear $selectedMonth$selectedDayOfMonth")
        return if (selectedDate.after(currentDate)) {
            // Выбранная дата является будущей
            "$selectedDayOfMonth/$selectedMonth/ $selectedYear"
        } else {
            "Срок не обозначена"
        }
    }

    private fun getCorrectSecondsTime(): Int {
        val strMin = binding!!.minutesEditText.text
        val strSec = binding!!.secondsEditText.text
        if (strMin.isEmpty() || strSec.isEmpty()) {
            informUser("Время пустое!", "Попробуйте еще раз!", R.drawable.incorrect)
            return 0
        }
        val minutes = strMin.toString().toInt()
        if (minutes < 1) {
            informUser("Слишком мало времени!", "Поставьте минимум минуту!", R.drawable.incorrect)
            return 0
        }
        val seconds = strSec.toString().toInt()
        println("$minutes $seconds")
        println(minutes * 60 + seconds)
        return if (minutes > 0)
            minutes * 60 + seconds
        else 0
    }

    private fun informUser(title: String, message: String, image: Int) {
        val dialogFragment = InformDialog(title, message, image)
        dialogFragment.show(parentFragmentManager, "inform about send task")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}