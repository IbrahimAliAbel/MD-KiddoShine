package com.example.kiddoshine.dataanak.prediksi


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kiddoshine.R
import com.example.kiddoshine.api.Model.PredictionDetails
import com.example.kiddoshine.api.repository.UserPreferences
import com.example.kiddoshine.databinding.ActivityInputDataStuntingBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class InputDataStunting : AppCompatActivity() {

    private lateinit var binding: ActivityInputDataStuntingBinding
    private lateinit var viewModel: PredictViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataStuntingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PredictViewModel::class.java)
        val userPreferences = UserPreferences(this)

        setupSpinner()

        lifecycleScope.launch {
            val userId = userPreferences.getUserId().first()

            if (userId.isEmpty()) {
                Toast.makeText(this@InputDataStunting, "User ID tidak ditemukan", Toast.LENGTH_SHORT).show()
                finish()
                return@launch
            }
        binding.btnUpload.setOnClickListener {
            processInputData(userId)

        }
}
        observePredictionResult()
    }

    private fun setupSpinner() {
        val genderOptions = arrayOf("Laki-Laki", "Perempuan")
        val breastfeedingOptions = arrayOf("ASI eklusif", "ASI non-eklusif")

        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderOptions)
        val breastfeedingAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, breastfeedingOptions)

        binding.spinnerJenisKelamin.adapter = genderAdapter
        binding.spinnerMenyusui.adapter = breastfeedingAdapter
    }

    private fun processInputData(userId: String) {
        val gender = binding.spinnerJenisKelamin.selectedItem.toString()
        val age = binding.etUmuranak.text.toString().toIntOrNull()
        val birthWeight = binding.etBeratBadanLahir.text.toString().toDoubleOrNull()
        val birthLength = binding.etTinggiBadanLahir.text.toString().toDoubleOrNull()
        val bodyWeight = binding.etBeratBadan.text.toString().toDoubleOrNull()
        val bodyLength = binding.etTinggiBadan.text.toString().toDoubleOrNull()
        val breastfeeding = when (binding.spinnerMenyusui.selectedItem.toString()) {
            "ASI eklusif" -> 1
            "ASI non-eklusif" -> 0
            else -> null
        }

        if (age == null || birthWeight == null || birthLength == null || bodyWeight == null || bodyLength == null || breastfeeding == null) {
            Toast.makeText(this, "Mohon isi semua data dengan benar", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.makePrediction(
            userId = userId,
            gender = gender,
            age = age,
            birthWeight = birthWeight,
            birthLength = birthLength,
            bodyWeight = bodyWeight,
            bodyLength = bodyLength,
            breastfeeding = breastfeeding
        )
    }

    private fun observePredictionResult() {
        viewModel.predictResult.observe(this) { result ->
            when (result) {
                is PredictionResult.Loading ->  binding.progressBar.visibility = android.view.View.VISIBLE
                is PredictionResult.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    val stunting = if (result.response.data.stunting == 1) "Beresiko Stunting" else "Tidak Beresiko Stunting"
                    Toast.makeText(this, "Result: $stunting", Toast.LENGTH_LONG).show()
                    showPredictionDialog(stunting, result.response.data.details)
                }
                is PredictionResult.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    Toast.makeText(this, "Error: ${result.errorMessage}", Toast.LENGTH_LONG).show()
                }
                else -> Unit
            }
        }
    }

    private fun showPredictionDialog(stuntingResult: String, details: PredictionDetails) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_prediction, null)
        val dialog = AlertDialog.Builder(this).setView(dialogView).create()

        dialog.window?.setLayout(
            resources.displayMetrics.widthPixels * 90 / 100,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        val tvDialogTitle = dialogView.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDialogContent = dialogView.findViewById<TextView>(R.id.tvDialogContent)
        val btnCloseDialog = dialogView.findViewById<Button>(R.id.btnCloseDialog)

        val message = """
            Hasil Prediksi: ${stuntingResult}
            Usia: ${details.age} bulan
            Berat Badan Lahir: ${details.birthWeight} kg
            Tinggi Badan Lahir: ${details.birthLength} cm
            Berat Badan Sekarang: ${details.bodyWeight} kg
            Tinggi Badan Sekarang: ${details.bodyLength} cm
            ASI: ${if (details.breastfeeding == 1) "Eklusif" else "Non-eklusif"}
        """.trimIndent()

        tvDialogContent.text = message

        btnCloseDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
