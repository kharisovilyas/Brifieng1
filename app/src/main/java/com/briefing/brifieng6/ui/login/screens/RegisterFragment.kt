package com.briefing.brifieng6.ui.login.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.server.domain.RegisterController
import com.briefing.brifieng6.ui.student.HomeStudFragment
import com.briefing.brifieng6.ui.teacher.HomeTeachFragment
import com.briefing.test.R
import com.briefing.test.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.register.setOnClickListener { sendRegisterData() }
    }

    private fun sendRegisterData() {
        val login = binding!!.loginInputEditText.text.toString()
        val password = binding!!.passwordInputEditText.text.toString()
        val email = binding!!.emailInputEditText.text.toString()
        val name = binding!!.nameInputEditText.text.toString()
        val surname = binding!!.surnameInputEditText.text.toString()
        val group = binding!!.groupInputEditText.text.toString()
        val isTeacher = binding!!.checkbox.isChecked
        if (login.isEmpty() && password.isEmpty()
            && email.isEmpty() && name.isEmpty()
            && surname.isEmpty() && group.isEmpty()
        ) {
            //TODO: запустить TOAST
            Log.d("ПРОВАЛЬНО", "данные пустые")
        } else {
            Log.d("УСПЕШНО", "соединение устанавливается")
            val registerController = RegisterController()
            registerController.register(login, password, email, name, surname, isTeacher)
            initUser(isTeacher)
        }
    }

    private fun initUser(isTeacher: Boolean) {
        if (isTeacher)
            launchApp(HomeStudFragment())
        else
            launchApp(HomeTeachFragment())
    }

    private fun launchApp(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}