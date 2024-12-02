package com.example.kiddoshine.dataanak.prediksi

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kiddoshine.dataanak.AkunAnak
import com.example.kiddoshine.databinding.ActivityInputDataStuntingBinding
import com.example.kiddoshine.dataanak.AnakViewModel
import androidx.lifecycle.Observer
import com.example.kiddoshine.R


class InputDataStunting : AppCompatActivity() {

    private lateinit var binding: ActivityInputDataStuntingBinding
    private lateinit var anakViewModel: AnakViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataStuntingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel
        anakViewModel = ViewModelProvider(this).get(AnakViewModel::class.java)

        setupSpinners()


        binding.btnUpload.setOnClickListener {
            // Mengamati LiveData untuk mendapatkan ID anak terbaru
            anakViewModel.getLatestAnakId().observe(this, Observer { anakId ->
                if (anakId != 0) {
                    val intent = Intent(this, AkunAnak::class.java)
                    intent.putExtra("ANAK_ID", anakId) // Mengirimkan ID anak yang relevan
                    startActivity(intent)
                    finish() // Menutup InputDataStunting activity
                } else {
                    // Tampilkan error jika ID anak tidak valid
                    Toast.makeText(this, "ID Anak tidak valid", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setupSpinners() {
        // Inisialisasi Spinner untuk jenis kelamin
        val jenisKelaminAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_kelamin_array, // Pastikan array ini ada di res/values/strings.xml
            android.R.layout.simple_spinner_item
        )
        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerJenisKelamin.adapter = jenisKelaminAdapter
    }

}
