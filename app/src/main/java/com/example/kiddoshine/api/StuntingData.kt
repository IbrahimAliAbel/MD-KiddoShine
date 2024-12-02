package com.example.kiddoshine.api
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StuntingData(
    val jenisKelamin: String,
    val umur: Int,
    val beratBadan: Int,
    val tinggiBadan: Int,
    val beratBadanLahir: Int,
    val tinggiBadanLahir: Int
) : Parcelable