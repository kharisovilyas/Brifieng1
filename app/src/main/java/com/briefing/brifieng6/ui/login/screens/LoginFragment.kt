package com.briefing.brifieng6.ui.login.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.test.R
import com.briefing.test.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.register.setOnClickListener { launchRegister() }
        binding!!.login.setOnClickListener { sendLoginData() }
    }

    private fun sendLoginData() {

    }

    private fun launchRegister() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RegisterFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}