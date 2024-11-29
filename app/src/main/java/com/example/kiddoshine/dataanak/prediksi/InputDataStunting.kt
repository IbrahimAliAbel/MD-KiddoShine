package com.example.kiddoshine.dataanak.prediksi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddoshine.dataanak.AkunAnak
import com.example.kiddoshine.databinding.ActivityInputDataStuntingBinding



class InputDataStunting : AppCompatActivity() {

    private lateinit var binding:ActivityInputDataStuntingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataStuntingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpload.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, AkunAnak::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Menutup AkunAnak activity

        }
    }
}