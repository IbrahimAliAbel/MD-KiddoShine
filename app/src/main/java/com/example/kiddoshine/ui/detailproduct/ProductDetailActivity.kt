package com.example.kiddoshine.ui.detailproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddoshine.R
import com.example.kiddoshine.databinding.ActivityProductDetailBinding
import java.text.NumberFormat
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var productNameTextView: TextView
    private lateinit var productDescriptionTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productImageView: ImageView
    private lateinit var buyButton: Button
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.detail)
        }

        productNameTextView = findViewById(R.id.productName)
        productDescriptionTextView = findViewById(R.id.productDescription)
        productPriceTextView = findViewById(R.id.productPrice)
        productImageView = findViewById(R.id.productImage)
        buyButton = findViewById(R.id.tombolBeli)

        val productName = intent.getStringExtra("product_name")
        val productDescription = intent.getStringExtra("product_description")
        val productPrice = intent.getDoubleExtra("product_price", 0.0)
        val productImageResId = intent.getIntExtra("product_image", 0)
        val productUrl = intent.getStringExtra("product_url")


        productNameTextView.text = productName
        productDescriptionTextView.text = productDescription
        productPriceTextView.text = getFormattedPrice(productPrice)
        productImageView.setImageResource(productImageResId)

        buyButton.setOnClickListener {
            productUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }

    private fun getFormattedPrice(price: Double): String {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(price)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}