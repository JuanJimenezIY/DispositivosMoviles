package com.example.dispositivos.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.example.dispositivos.R
import com.example.dispositivos.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCapture.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResult.launch(intent)
        }
        binding.imageCapture.setOnClickListener{
            var shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Hola como estas")
            shareIntent.setType("text/plain")
            startActivity(Intent.createChooser(shareIntent,"Compartir"))
        }
    }

    private val cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        when(result.resultCode){
            Activity.RESULT_OK->{
                    val image =result.data?.extras?.get("data") as Bitmap
                    binding.imageCapture.setImageBitmap(image)
            }
            Activity.RESULT_CANCELED->{


            }
        }
    }
}