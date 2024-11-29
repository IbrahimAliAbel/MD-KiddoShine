package com.example.kiddoshine.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anak_table")
data class Anak(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val usia: String = "undefined",
    val jenisKelamin: String,
    val kelahiran: String,
    val golonganDarah: String,
    val alergi: String,
    val lingkarKepala: String = "undefined",
    val tinggiBadan: String,
    val beratBadan: String,
    val photoPath: String? = null,
    val new_column: String? = "unknown",
    val tanggalLahir: String// Kolom baru yang ditambahkan
)
