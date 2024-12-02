package com.example.kiddoshine.dataanak

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kiddoshine.MainActivity
import androidx.lifecycle.Observer
import com.example.kiddoshine.R
import com.example.kiddoshine.dataanak.prediksi.InputDataStunting
import com.example.kiddoshine.database.Anak
import com.example.kiddoshine.databinding.ActivityAkunanakBinding
import java.io.File



class AkunAnak : AppCompatActivity() {

    // Inisialisasi View Binding
    private lateinit var binding: ActivityAkunanakBinding
    private lateinit var anakViewModel: AnakViewModel
    private var anakId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menggunakan View Binding untuk mengatur konten
        binding = ActivityAkunanakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        anakViewModel = ViewModelProvider(this).get(AnakViewModel::class.java)

        // Ambil ID anak dari Intent
        anakId = intent.getIntExtra("ANAK_ID", 0)
        Log.d("AkunAnak", "Received Anak ID: $anakId")

        anakViewModel.getAnakById(anakId).observe(this, Observer { anak ->
            if (anak != null) {
                Log.d("AkunAnak", "Received Anak data: ${anak.nama}, ${anak.usia}, ${anak.jenisKelamin}")
                displayAnakData(anak)
            } else {
                Log.d("AkunAnak", "Anak dengan ID $anakId tidak ditemukan.")
            }
        })


        binding.btnBackToList.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Menutup AkunAnak activity
        }

        binding.btnInput.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, InputDataStunting::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Menutup AkunAnak activity
        }

        binding.btnUpdate.setOnClickListener {
            val intent = Intent(this, InputAkunAnak::class.java)
            intent.putExtra("ANAK_ID", anakId)  // Kirim ID anak untuk update
            startActivity(intent)
        }
    }

    private fun displayAnakData(anak: Anak) {
        binding.tvNamaAnak.text = anak.nama
        binding.tvUsiaanak.text = "Usia : ${anak.usia}"
        binding.tvJenisKelamin.text = "Jenis Kelamin: ${anak.jenisKelamin}"
        binding.tvKelahiran.text = "Kelahiran: ${anak.kelahiran}"
        binding.tvGolonganDarah.text = "Golongan Darah: ${anak.golonganDarah}"
        binding.tvAlergi.text = "Alergi: ${anak.alergi}"
        binding.tvLingkarKepala.text = "Lingkar Kepala: ${anak.lingkarKepala} cm"
        binding.tvTinggiBadan.text = "${anak.tinggiBadan} cm"
        binding.tvBeratBadan.text = "${anak.beratBadan} kg"
        binding.tvTanggalLahir.text = "Tanggal Lahir: ${anak.tanggalLahir}"
        // Tampilkan foto anak jika ada
        if (!anak.photoPath.isNullOrEmpty()) {
            val file = File(anak.photoPath)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                binding.ivProfilePicture.setImageBitmap(bitmap)
            } else {
                binding.ivProfilePicture.setImageResource(R.drawable.baseline_face_24)
            }
        } else {
            binding.ivProfilePicture.setImageResource(R.drawable.baseline_face_24)
        }
    }

    private fun loadImageFromUri(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.ivProfilePicture.setImageBitmap(bitmap)
            inputStream?.close()
            Log.d("AkunAnak", "Photo loaded successfully from URI: $uri")
        } catch (e: Exception) {
            Log.e("AkunAnak", "Error loading image from URI", e)
            binding.ivProfilePicture.setImageResource(R.drawable.baseline_face_24)
        }
    }

}