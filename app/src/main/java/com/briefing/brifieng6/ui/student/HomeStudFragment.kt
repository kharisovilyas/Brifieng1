package com.briefing.brifieng6.ui.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.ui.login.screens.RegisterFragment
import com.briefing.brifieng6.ui.teacher.HomeTeachFragment
import com.briefing.test.R
import com.briefing.test.databinding.HomeTeachFragmentBinding

class HomeStudFragment : Fragment() {
    private var binding: HomeTeachFragmentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeTeachFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}