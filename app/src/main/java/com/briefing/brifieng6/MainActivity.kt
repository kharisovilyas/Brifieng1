package com.briefing.brifieng6

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.ui.login.screens.LoginFragment
import com.briefing.brifieng6.ui.student.screens.HomeStudFragment
import com.briefing.brifieng6.ui.teacher.screens.HomeTeachFragment
import com.briefing.test.R
import com.briefing.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val isTeacher: Boolean
        if (!sharedPreferences.getBoolean("IS_FIRST_RUN", true)) {
            // Приложение уже запускалось ранее
            isTeacher = sharedPreferences.getBoolean("IS_TEACHER", false)
            startApp(isTeacher)
        } else {
            initUser()
        }

        setContentView(binding!!.root)
    }

    private fun startApp(teacher: Boolean) {
        val fragment: Fragment =
            if(teacher) HomeTeachFragment()
            else HomeStudFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment)
            .commit()
    }

    private fun initUser(){
        val loginFragment = LoginFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, loginFragment)
            .commit()
    }
}