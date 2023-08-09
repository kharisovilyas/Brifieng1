package com.briefing.brifieng6.ui.login.useralert

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.briefing.test.R
import com.briefing.test.databinding.GreetingsDialogBinding


class InformDialog(
    private val title: String,
    private val details: String,
    private val image: Int
) : DialogFragment() {
    private var binding: GreetingsDialogBinding? = null
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
        binding!!.image.setOnClickListener{
            dismiss()
        }
        return binding!!.root
    }

    @SuppressLint("ResourceAsColor")
    private fun setViewData() {
        binding!!.titleOfDialog.text = title
        binding!!.description.text = details
        binding!!.image.setImageResource(image)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}