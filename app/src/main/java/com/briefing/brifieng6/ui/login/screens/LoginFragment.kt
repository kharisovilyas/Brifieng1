package com.briefing.brifieng6.ui.login.screens

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.api.model.LoginReceiveRemote
import com.briefing.brifieng6.ui.login.useralert.InformDialog
import com.briefing.brifieng6.ui.login.viewmodel.LoginViewModel
import com.briefing.brifieng6.ui.login.viewmodel.RegisterViewModel
import com.briefing.brifieng6.ui.student.screens.HomeStudFragment
import com.briefing.brifieng6.ui.teacher.screens.HomeTeachFragment
import com.briefing.test.R
import com.briefing.test.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

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
        val login = binding!!.loginInputEditText.text.toString()
        val password = binding!!.passwordInputEditText.text.toString()

        var hasEmptyFields = false

        // Проверяем все TextInputLayout на наличие пустых полей
        if (login.isEmpty()) {
            binding!!.loginInputEditText.error = "Заполните поле"
            hasEmptyFields = true
        }

        if (password.isEmpty()) {
            binding!!.passwordInputEditText.error = "Заполните поле"
            hasEmptyFields = true
        }
        // Если есть пустые поля, выполните необходимые действия
        if (hasEmptyFields) {
            informUser(
                "Пустые поля!",
                "Заполните необходимые поля",
                R.drawable.incorrect
            )
        } else {
            val loginViewModel = LoginViewModel()
            loginViewModel.enterUser(
                LoginReceiveRemote(
                    login = login,
                    password_ = password,
                )
            )
            var isTeacher = false
            loginViewModel.errorMessage.observe(viewLifecycleOwner) {
                informUser("Ошибка!", it, R.drawable.incorrect)
            }
            loginViewModel.message.observe(viewLifecycleOwner){
                informUser("С возвращением!", it, R.drawable.correct)
            }
            loginViewModel.token.observe(viewLifecycleOwner) {
                saveTokenInSharedPreference(it)
            }
            loginViewModel.teacher.observe(viewLifecycleOwner) {
                isTeacher = it
                saveRoleUserInSharedPreference(it)
            }
            loginViewModel.isSuccessfulLogin.observe(viewLifecycleOwner) { isSuccessful ->
                if (isSuccessful) {
                    initUser(isTeacher)
                    saveUserdataInSharedPreference(login)
                }
            }
        }
    }

    private fun saveUserdataInSharedPreference(login: String) {
        val registerViewModel = RegisterViewModel()
        registerViewModel.fetchUserData(login)
        registerViewModel.userdata.observe(viewLifecycleOwner){
            val sharedPreferences =
                requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("login", it.login)
            editor.putString("email", it.email)
            editor.putString("name", it.name_)
            editor.putString("surname", it.surname)
            if (!it.teacher) editor.putString("group", it.group_)
            editor.putString("university", it.university)
            editor.apply()
        }
    }

    private fun informUser(title: String, message: String, image: Int) {
        val dialogFragment = InformDialog(title, message, image)
        dialogFragment.show(parentFragmentManager, "inform about login")
    }

    private fun saveTokenInSharedPreference(token: String?) {
        val sharedPref = context?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        sharedPref?.edit()?.putString("TOKEN", token)?.apply()
    }

    private fun saveRoleUserInSharedPreference(isTeacher: Boolean) {
        val sharedPref = context?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        sharedPref?.edit()?.putBoolean("IS_TEACHER", isTeacher)?.apply()
        sharedPref?.edit()?.putBoolean("IS_FIRST_RUN", false)?.apply()
    }

    private fun initUser(isTeacher: Boolean) {
        Log.d("ВЫ УЧИТЕЛЬ?", "" + isTeacher)
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

    private fun launchRegister() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}