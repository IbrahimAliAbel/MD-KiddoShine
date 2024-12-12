package com.example.kiddoshine.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.kiddoshine.R
import com.example.kiddoshine.ui.home.HomeFragment
import com.example.kiddoshine.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref: SharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isFirstRun = sharedPref.getBoolean("isFirstRun", true)

        if (isFirstRun) {
        setContentView(R.layout.activity_splashscreen)

            val viewPager = findViewById<ViewPager2>(R.id.viewPager)
            val fragmentAdapter = FragmentAdapter(this)
            viewPager.adapter = fragmentAdapter

            val pageIndicator = findViewById<TextView>(R.id.pageIndicator)

            val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
            TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    val currentPage = position + 1
                    val totalPages = fragmentAdapter.itemCount
                    pageIndicator.text = "Geser ke kanan"
                }
            })

            val editor = sharedPref.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()
        } else {
            setContentView(R.layout.activity_splashscreen)
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1000)
        }
    }
}

class FragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SplashLogo()
            1 -> SplashInfo()
            else -> SplashDeveloper()
        }
    }
}
