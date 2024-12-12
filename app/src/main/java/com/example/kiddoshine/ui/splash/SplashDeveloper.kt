package com.example.kiddoshine.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.kiddoshine.R
import com.example.kiddoshine.ui.login.LoginActivity

class SplashDeveloper : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.splash_developer, container, false)

        val startButton = rootView.findViewById<Button>(R.id.startButton)

        startButton.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return rootView
    }
}