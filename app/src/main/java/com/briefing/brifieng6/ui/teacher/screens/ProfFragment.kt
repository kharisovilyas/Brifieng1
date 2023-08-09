package com.briefing.brifieng6.ui.teacher.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briefing.brifieng6.ui.login.screens.LoginFragment
import com.briefing.brifieng6.ui.student.recycler.model.ItemTaskData
import com.briefing.brifieng6.ui.student.screens.ToAnswerFragment
import com.briefing.test.R
import com.briefing.test.databinding.FragmentToAnswerBinding
import com.briefing.test.databinding.ProfileFragmentBinding
import com.briefing.test.databinding.StatisticFragmentBinding

class ProfFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.exitProfile.setOnClickListener {
            removeDataFromSharedPreference()

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, LoginFragment())
                .addToBackStack(null)
                .commit()

        }
        return binding.root
    }

    private fun removeDataFromSharedPreference() {
        val sharedPreferences =
            requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("login")
        editor.remove("email")
        editor.remove("name")
        editor.apply()
        val sharedPref = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editorPref = sharedPref.edit()
        editorPref.remove("IS_TEACHER")
        editorPref.remove("IS_FIRST_RUN")
        editorPref.apply()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDataFromSharedPreferences()
    }

    private fun loadDataFromSharedPreferences() {
        val sharedPreferences =
            requireContext().getSharedPreferences("userdata", Context.MODE_PRIVATE)

        val login = sharedPreferences.getString("login", "")
        val email = sharedPreferences.getString("email", "")
        val name = sharedPreferences.getString("name", "")
        val surname = sharedPreferences.getString("surname", "")
        val group = sharedPreferences.getString("group", "")
        val university = sharedPreferences.getString("university", "")

        val sharedPref = context?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val teacher = sharedPref?.getBoolean("IS_TEACHER", false) ?: false

        binding.textViewLogin.text = login
        binding.textViewEmail.text = email
        binding.textViewName.text = name
        binding.textViewSurname.text = surname
        binding.textViewGroup.text = group
        binding.textViewUniversity.text = university
        if (teacher) binding.textViewGroup.visibility = View.GONE
    }
}