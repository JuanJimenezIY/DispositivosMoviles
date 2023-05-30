package com.example.dispositivos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivos.databinding.ActivityEmptyBinding
import com.google.android.material.snackbar.Snackbar

class EmptyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onStart() {
        super.onStart()
        initClass()
    }
    private fun initClass(){
        binding.button1.setOnClickListener{
            var f = Snackbar.make(
                binding.button1,
                "EmptyActivity",
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
    }
}