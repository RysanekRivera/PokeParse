package com.rysanek.pokeparse.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rysanek.pokeparse.databinding.ActivityMainBinding
import com.rysanek.pokeparse.utils.setUpSystemWindow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        setUpSystemWindow()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}