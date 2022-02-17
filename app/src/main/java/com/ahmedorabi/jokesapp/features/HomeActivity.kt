package com.ahmedorabi.jokesapp.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmedorabi.jokesapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}