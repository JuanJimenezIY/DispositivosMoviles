package com.example.dispositivos.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivos.R
import com.example.dispositivos.databinding.ActivityDetailsMarvelItemBinding
import com.example.dispositivos.databinding.MarvelCharactersBinding
import com.example.dispositivos.ui.utilities.MarvelChars
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        /*
        var name : String?=""
        intent.extras?.let{
             name =it.getString("name")
        }
        if (!name.isNullOrEmpty()){
            binding.textoCentrado.text=name
        }
*/
        val item=intent.getParcelableExtra<MarvelChars>("name")
        if (item!= null){
            binding.textoCentrado.text=item.name
            binding.txtComic.text=item.comic

            Picasso.get().load(item.image).into(binding.imgPersonaje)
        }
    }
}