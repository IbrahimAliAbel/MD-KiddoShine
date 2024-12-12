package com.example.kiddoshine.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.kiddoshine.R
import com.example.kiddoshine.api.repository.UserPreferences
import com.example.kiddoshine.repository.ViewModelFactory
import com.example.kiddoshine.ui.login.LoginActivity


class SettingsFragment : Fragment() {
    private lateinit var logoutButton: Button

    private val settingsViewModel: SettingsViewModel by activityViewModels {
        ViewModelFactory(UserPreferences.getInstance(requireContext()), requireContext()) // Pass both context and userPreferences
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_settings, container, false)

        logoutButton = binding.findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            settingsViewModel.logout()
            navigateToLogin()
        }

        return binding
    }

    private fun navigateToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}