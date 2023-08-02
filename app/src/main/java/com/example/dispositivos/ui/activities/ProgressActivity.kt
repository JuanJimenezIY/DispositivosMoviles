package com.example.dispositivos.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dispositivos.R
import com.example.dispositivos.databinding.ActivityNotificationBinding
import com.example.dispositivos.databinding.ActivityProgressActivtyBinding
import com.example.dispositivos.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgressActivtyBinding
    private val progressviewmodel by viewModels<ProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityProgressActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressviewmodel.progressState.observe(this,{
            binding.progressBar.visibility= it
        })


        binding.btnProceso.setOnClickListener {

          progressviewmodel.processBackground(3000)

        }

        progressviewmodel.progressState.observe(this,{
            binding.progressBar.visibility=it
        })

        progressviewmodel.items.observe(this,{
            Toast.makeText(this,it[10].name,Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,NotificationActivity::class.java))
        })
        binding.btnProceso2.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                progressviewmodel.getMarvelChars(0,90)
            }

        }

    }
}