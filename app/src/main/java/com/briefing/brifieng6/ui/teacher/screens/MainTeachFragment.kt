package com.briefing.brifieng6.ui.teacher.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.ui.teacher.screens.statistic.StatisticFragment
import com.briefing.test.R
import com.briefing.test.databinding.MainTeachFragmentBinding

class MainTeachFragment : Fragment() {
    private var binding: MainTeachFragmentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainTeachFragmentBinding.inflate(inflater, container, false)
        startNewFragment(HomeTeachFragment())
        binding!!.teachBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    startNewFragment(HomeTeachFragment())
                    true
                }
                R.id.navigation_statistic -> {
                    startNewFragment(StatisticFragment())
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
            .replace(R.id.main_teach_container, fragment)
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
