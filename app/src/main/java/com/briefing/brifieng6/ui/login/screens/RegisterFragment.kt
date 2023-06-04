package com.briefing.brifieng6.ui.login.screens

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.api.model.RegisterReceiveRemote
import com.briefing.brifieng6.ui.login.viewmodel.RegisterViewModel
import com.briefing.brifieng6.ui.student.screens.HomeStudFragment
import com.briefing.brifieng6.ui.teacher.screens.HomeTeachFragment
import com.briefing.test.R
import com.briefing.test.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null
    private var rowLogin: String = generateLogin()
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
        binding!!.loginText.text = rowLogin
        binding!!.register.setOnClickListener {
            sendRegisterData()
        }
        binding!!.exit.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding!!.generateLogin.setOnClickListener{
            rowLogin = generateLogin()
            binding!!.loginText.text = rowLogin
        }
    }

    private fun sendRegisterData() {
        val login = rowLogin
        val password = binding!!.passwordInputEditText.text.toString()
        val email = binding!!.emailInputEditText.text.toString()
        val name = binding!!.nameInputEditText.text.toString()
        val surname = binding!!.surnameInputEditText.text.toString()
        val university = binding!!.univInputEditText.text.toString()
        val group = binding!!.groupInputEditText.text.toString()
        val isTeacher = binding!!.checkbox.isChecked
        var hasEmptyFields = false

        // Проверяем все TextInputLayout на наличие пустых полей
        if (login.isEmpty()) {
            hasEmptyFields = true
        }

        if (password.isEmpty()) {
            binding!!.passwordInputEditText.error = "Заполните поле"
            hasEmptyFields = true
        }

        if (email.isEmpty()) {
            binding!!.emailInputEditText.error = "Заполните поле"
            hasEmptyFields = true
        }

        if (name.isEmpty()) {
            binding!!.nameInputEditText.error = "Заполните поле"
            hasEmptyFields = true
        }

        if (surname.isEmpty()) {
            binding!!.surnameInputEditText.error = "Заполните поле"
            hasEmptyFields = true
        }

        if (university.isEmpty()) {
            binding!!.univInputEditText.error = "Заполните поле"
            hasEmptyFields = true
        }

        if (group.isEmpty() && !(isTeacher)) {
            binding!!.groupInputEditText.error = "Заполните поле"
            hasEmptyFields = true
        }

        // Если есть пустые поля, выполните необходимые действия
        if (hasEmptyFields) {
            informUser("Пустые поля!")
        } else {
            Log.d("УСПЕШНО", "соединение устанавливается")
            val registerViewModel = RegisterViewModel()
            registerViewModel.insertUser(
                RegisterReceiveRemote(
                    login = login,
                    password_ = password,
                    email = email,
                    name_ = name,
                    surname = surname,
                    group_ = group,
                    university = university,
                    teacher = isTeacher
                )
            )
            saveRoleUserInSharedPreference(isTeacher)
            registerViewModel.message.observe(viewLifecycleOwner){
                informUser(it)
            }
            registerViewModel.isSuccessfulRegister.observe(viewLifecycleOwner) {
                    isSuccessful ->
                if (isSuccessful) {
                    initUser(isTeacher)
                }
            }
        }
    }

    private fun informUser(message: String?) {
        val snack = Snackbar
            .make(binding!!.registerContainer, "Ошибка !", Snackbar.LENGTH_SHORT)
            .setAction(message) { }
        snack.setActionTextColor(Color.RED)
        snack.show()
    }

    private fun generateLogin(): String {
        val loginLength = 8 // Длина логина
        val allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" // Разрешенные символы
        val random = Random()

        val loginBuilder = StringBuilder(loginLength)
        for (i in 0 until loginLength) {
            val randomIndex = random.nextInt(allowedChars.length)
            val randomChar = allowedChars[randomIndex]
            loginBuilder.append(randomChar)
        }

        return loginBuilder.toString()
    }

    private fun saveRoleUserInSharedPreference(isTeacher: Boolean) {
        val sharedPref = context?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        sharedPref?.edit()?.putBoolean("IS_TEACHER", isTeacher)?.apply()
        sharedPref?.edit()?.putBoolean("IS_FIRST_RUN", false)?.apply()
    }

    private fun initUser(isTeacher: Boolean) {
        if (isTeacher)
            launchApp(HomeTeachFragment())
        else
            launchApp(HomeStudFragment())
    }

    private fun launchApp(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}