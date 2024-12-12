package com.example.kiddoshine.dataanak

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kiddoshine.R
import com.example.kiddoshine.database.Anak
import com.example.kiddoshine.databinding.ActivityInputakunanakBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class InputAkunAnak : AppCompatActivity() {


    private lateinit var binding: ActivityInputakunanakBinding
    private lateinit var anakViewModel: AnakViewModel
    private var anakId: Int? = null

    private val REQUEST_IMAGE_CAPTURE = 1
    private var currentPhotoPath: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputakunanakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        anakViewModel = ViewModelProvider(this).get(AnakViewModel::class.java)


                anakId = intent.getIntExtra("anak_id", -1).takeIf { it != -1 }

        if (anakId != null) {
            loadAnakData(anakId!!)
        } else {

            supportActionBar?.title = "Tambah Akun Anak"
        }


        setupSpinners()

        binding.btnCamera.setOnClickListener {
            showImagePickerDialog()
        }

        binding.btnSubmit.setOnClickListener {
            if (anakId != null) {

                updateAnakData()
            } else {

                saveAnakData()
            }
        }

        binding.etTanggallahir.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->

                val formattedDate = String.format("%02d-%02d-%04d", selectedDayOfMonth, selectedMonth + 1, selectedYear)
                binding.etTanggallahir.setText(formattedDate)
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
    }

    private fun setupSpinners() {
        val jenisKelaminAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_kelamin_array,
            android.R.layout.simple_spinner_item
        )
        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerJenisKelamin.adapter = jenisKelaminAdapter

        val kelahiranAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.kelahiran_array,
            android.R.layout.simple_spinner_item
        )
        kelahiranAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKelahiran.adapter = kelahiranAdapter

        val golonganDarahAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.golongan_darah_array,
            android.R.layout.simple_spinner_item
        )
        golonganDarahAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGolongandarah.adapter = golonganDarahAdapter

        val alergiAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.alergi_array,
            android.R.layout.simple_spinner_item
        )
        alergiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAlergi.adapter = alergiAdapter
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Ambil Foto", "Pilih dari Galeri")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih Gambar")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> captureImage()
                1 -> startGallery()
            }
        }
        builder.show()
    }

    private fun captureImage() {

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = androidx.core.content.FileProvider.getUriForFile(
                    this,
                    "${applicationContext.packageName}.fileprovider",
                    it
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    } else {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CODE_CAMERA
        )
    }
}
    private val REQUEST_CODE_CAMERA = 100

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                captureImage()
            } else {

                Toast.makeText(this, "Permission to use camera is required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val copiedPath = copyUriToInternalStorage(uri)
            if (copiedPath != null) {
                currentPhotoPath = copiedPath
                showImage(copiedPath)
                Log.d("Photo Picker", "Media copied to: $currentPhotoPath")
            } else {
                Log.e("Photo Picker", "Failed to copy media")
            }
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }


    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private fun showImage(path: String) {
        val file = File(path)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            binding.ivProfilePicture.setImageBitmap(bitmap)
        }
    }


    private fun copyUriToInternalStorage(uri: Uri): String? {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val file = File(getExternalFilesDir(null), fileName)
            val outputStream = file.outputStream()

            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            return file.absolutePath
        } catch (e: Exception) {
            Log.e("InputAkunAnak", "Error copying file to internal storage", e)
        }
        return null
    }

private fun saveAnakData() {
    val namaAnak = binding.etNamaAnak.text.toString().trim()
    val usiaAnak = binding.etUsiaAnak.text.toString().trim()
    val tinggiBadan = binding.etTinggiBadan.text.toString().trim()
    val jenisKelamin = binding.spinnerJenisKelamin.selectedItem.toString()
    val kelahiran = binding.spinnerKelahiran.selectedItem.toString()
    val golonganDarah = binding.spinnerGolongandarah.selectedItem.toString()
    val alergi = binding.spinnerAlergi.selectedItem.toString()
    val lingkarKepala = binding.etLingkarkepala.text.toString().trim()
    val beratBadan = binding.etBeratBadan.text.toString().trim()
    val tanggalLahir = binding.etTanggallahir.text.toString().trim()

    if (namaAnak.isEmpty() || usiaAnak.isEmpty() || tinggiBadan.isEmpty()) {
        Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
        return
    }

    val anak = Anak(
        nama = namaAnak,
        usia = usiaAnak,
        jenisKelamin = jenisKelamin,
        kelahiran = kelahiran,
        golonganDarah = golonganDarah,
        alergi = alergi,
        lingkarKepala = lingkarKepala,
        tinggiBadan = tinggiBadan,
        beratBadan = beratBadan,
        photoPath = currentPhotoPath,
        tanggalLahir = tanggalLahir
    )
    anakViewModel.insert(anak)
    Toast.makeText(this, "Data Anak Disimpan", Toast.LENGTH_SHORT).show()
    finish()
    }

    private fun loadAnakData(anakId: Int) {

        anakViewModel.getAnakById(anakId).observe(this, { anak ->
            anak?.let {
                binding.etNamaAnak.setText(it.nama)
                binding.etUsiaAnak.setText(it.usia)
                binding.spinnerJenisKelamin.setSelection(getSpinnerIndex(binding.spinnerJenisKelamin, it.jenisKelamin))
                binding.spinnerKelahiran.setSelection(getSpinnerIndex(binding.spinnerKelahiran, it.kelahiran))
                binding.spinnerGolongandarah.setSelection(getSpinnerIndex(binding.spinnerGolongandarah, it.golonganDarah))
                binding.spinnerAlergi.setSelection(getSpinnerIndex(binding.spinnerAlergi, it.alergi))
                binding.etLingkarkepala.setText(it.lingkarKepala)
                binding.etTinggiBadan.setText(it.tinggiBadan)
                binding.etBeratBadan.setText(it.beratBadan)
                binding.etTanggallahir.setText(it.tanggalLahir)
            }
        })
    }

    private fun getSpinnerIndex(spinner: Spinner, value: String): Int {

        val adapter = spinner.adapter as ArrayAdapter<String>
        return adapter.getPosition(value)
    }

    private fun updateAnakData() {
        val namaAnak = binding.etNamaAnak.text.toString().trim()
        val usiaAnak = binding.etUsiaAnak.text.toString().trim()
        val jenisKelamin = binding.spinnerJenisKelamin.selectedItem.toString()
        val kelahiran = binding.spinnerKelahiran.selectedItem.toString()
        val golonganDarah = binding.spinnerGolongandarah.selectedItem.toString()
        val alergi = binding.spinnerAlergi.selectedItem.toString()
        val lingkarKepala = binding.etLingkarkepala.text.toString().trim()
        val tinggiBadan = binding.etTinggiBadan.text.toString().trim()
        val beratBadan = binding.etBeratBadan.text.toString().trim()
        val tanggalLahir = binding.etTanggallahir.text.toString().trim()

        val anak = Anak(
            id = anakId!!,
            nama = namaAnak,
            usia = usiaAnak,
            jenisKelamin = jenisKelamin,
            kelahiran = kelahiran,
            golonganDarah = golonganDarah,
            alergi = alergi,
            lingkarKepala = lingkarKepala,
            tinggiBadan = tinggiBadan,
            beratBadan = beratBadan,
            tanggalLahir = tanggalLahir
        )

        anakViewModel.update(anak)
        Toast.makeText(this, "Data anak berhasil diperbarui", Toast.LENGTH_SHORT).show()
        finish()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
}