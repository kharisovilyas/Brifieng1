package com.briefing.brifieng6.ui.login.useralert

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.DialogFragment
import com.briefing.test.R
import com.briefing.test.databinding.GreetingsDialogBinding

class DialogGreetings(correctAnswer: Boolean, details: String) : DialogFragment() {
    private var binding: GreetingsDialogBinding? = null
    private val result = correctAnswer
    private val _details = details
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        dialog?.let {
            val width = ViewGroup.LayoutParams.WRAP_CONTENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GreetingsDialogBinding.inflate(inflater, container, false)
        setViewData()
        binding!!.btnClose.setOnClickListener {
            dismiss()
        }
        binding!!.correct.setOnClickListener{
            dismiss()
        }
        binding!!.incorrect.setOnClickListener{
            dismiss()
        }
        return binding!!.root
    }

    @SuppressLint("ResourceAsColor")
    private fun setViewData() {
        Log.d("ЗДЕСЬ!", _details)
        binding!!.description.text = _details
        if(result){
            binding!!.correct.visibility = View.VISIBLE
            binding!!.titleOfDialog.text = "Верно!"
            binding!!.titleOfDialog.setTextColor(R.color.teal)
        }
        else {
            binding!!.incorrect.visibility = View.VISIBLE
            binding!!.titleOfDialog.text = "Неверно!"
            binding!!.titleOfDialog.setTextColor(R.color.watermelon)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}