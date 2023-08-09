package com.briefing.brifieng6.ui.student.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.ui.teacher.screens.HomeTeachFragment
import com.briefing.brifieng6.ui.teacher.screens.ProfFragment
import com.briefing.brifieng6.ui.teacher.screens.statistic.StatisticFragment
import com.briefing.test.R
import com.briefing.test.databinding.MainStudFragmentBinding

class MainStudFragment: Fragment() {
    private var binding: MainStudFragmentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainStudFragmentBinding.inflate(inflater, container, false)
        startNewFragment(HomeStudFragment())
        binding!!.studBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    startNewFragment(HomeStudFragment())
                    true
                }
                R.id.navigation_prof -> {
                    startNewFragment(ProfFragment())
                    true
                }
                else -> false
            }
        }
        return binding!!.root
    }

    private fun startNewFragment(fragment: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_stud_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}