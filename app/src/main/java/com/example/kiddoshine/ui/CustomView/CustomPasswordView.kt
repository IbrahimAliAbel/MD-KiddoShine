package com.example.kiddoshine.ui.CustomView

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import com.example.kiddoshine.R


class CustomPasswordView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val editText: EditText

    init {
        inflate(context, R.layout.custom_password, this)
        editText = findViewById(R.id.ed_password)

        setupValidation()
    }

    private fun setupValidation() {
        editText.addTextChangedListener {
            val password = it.toString()
            if (password.length < 8) {
                editText.error = "Password harus lebih dari 8 karakter"
            }
        }
    }

    fun getText(): String = editText.text.toString()
}