package com.example.kiddoshine.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kiddoshine.api.repository.UserPreferences
import com.example.kiddoshine.databinding.ActivityRegisterBinding
import com.example.kiddoshine.repository.ViewModelFactory
import com.example.kiddoshine.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var registerViewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreferences = UserPreferences.getInstance(this)

        val viewModelFactory = ViewModelFactory(userPreferences, this)
        registerViewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)
        setupActions()
        observeRegisterStatus()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logoImageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val registex = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameviee = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nemelayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailee = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emaillayoutt= ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val pwtext = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val pwpw = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val buttonn = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val tanya = ObjectAnimator.ofFloat(binding.txttanya, View.ALPHA, 1f).setDuration(500)
        val tanya2 = ObjectAnimator.ofFloat(binding.tologin, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(registex, nameviee, nemelayout, emailee, emaillayoutt, pwtext,pwpw,buttonn, tanya, tanya2)
            start()
        }
    }

    private fun setupActions() {
        binding.signupButton.setOnClickListener {
            val nama = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            registerViewModel.registerUser(nama, email, password)
        }

        binding.tologin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
    }
    }

    private fun observeRegisterStatus() {
        lifecycleScope.launchWhenStarted {
            registerViewModel.registerStatus.collect { status ->
                when (status) {
                    is RegisterStatus.Loading -> {

                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is RegisterStatus.Success -> {
                        Toast.makeText(this@RegisterActivity, status.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish() // Selesai, tutup activity
                    }
                    is RegisterStatus.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, status.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}