package com.example.dispositivos.ui.activities

import android.content.Intent
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
            val i = Intent()
            i.putExtra("result","Resultado exitoso")


            setResult(RESULT_OK,i)
            finish()

        }

        binding.btnFalse.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

}