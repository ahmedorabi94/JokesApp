package com.ahmedorabi.jokesapp.features.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ahmedorabi.jokesapp.R
import com.ahmedorabi.jokesapp.databinding.ActivitySplashBinding
import com.ahmedorabi.jokesapp.features.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {


    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPreferences
    private var counterValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPref = getSharedPreferences(
            getString(R.string.preference_key), Context.MODE_PRIVATE
        )

        setCounterValue()
        displayCounterValue()


        lifecycleScope.launch {

            delay(3000)

            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setCounterValue() {
        with(sharedPref.edit()) {
            getCounterValue()
            putInt(getString(R.string.opened_times_key), counterValue + 1)
            apply()
        }
    }

    private fun getCounterValue() {
        counterValue = sharedPref.getInt(getString(R.string.opened_times_key), 0)
    }

    @SuppressLint("SetTextI18n")
    fun displayCounterValue() {
        getCounterValue()
        binding.openedCounterTv.text =
            getString(R.string.opened_times_app) + " $counterValue" + " Times"

    }
}