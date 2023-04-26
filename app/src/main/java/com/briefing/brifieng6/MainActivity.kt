package com.briefing.brifieng6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.briefing.brifieng6.ui.login.screens.LoginFragment
import com.briefing.test.R
import com.briefing.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var fragmentManager = supportFragmentManager
    private var fragmentTransaction = fragmentManager.beginTransaction()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val FIRST_ENTER = "isFirstEnter"
        val enter = getSharedPreferences(FIRST_ENTER, MODE_PRIVATE)
        var isFirstEnter = false
        var teacher = false
        if (enter.contains(FIRST_ENTER)) {
            isFirstEnter = enter.getBoolean(FIRST_ENTER, false)
        }
        if (!isFirstEnter) {
            teacher = initUser()
        } else {
            startApp(teacher)
        }
        setContentView(binding!!.root)
    }

    private fun startApp(teacher: Boolean) {}
    private fun initUser(): Boolean {
        val loginFragment = LoginFragment()
        fragmentTransaction.add(R.id.container, loginFragment).commit()
        //возможны проблемы
        return false
    }
}