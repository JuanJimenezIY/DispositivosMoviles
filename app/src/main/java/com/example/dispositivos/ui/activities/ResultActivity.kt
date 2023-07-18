package com.example.dispositivos.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivos.R
import com.example.dispositivos.databinding.ActivityMainBinding
import com.example.dispositivos.databinding.ActivityResultBinding
import com.example.dispositivos.databinding.MarvelCharactersGrindBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnResultOk.setOnClickListener{
            setResult(RESULT_OK)
            finish()

        }

        binding.btnFalse.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

}