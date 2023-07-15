package com.example.dispositivos.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.dispositivos.data.entities.marvel.characters.data.database.MarvelCharsDB
import com.example.dispositivos.logic.data.MarvelChars
import com.example.dispositivos.databinding.ActivityDetailsMarvelItemBinding
import com.example.dispositivos.logic.marvelLogic.MarvelLogic

import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            binding.descripcion.text=item.desc
        }
        binding.fButton.setOnClickListener{
            lifecycleScope.launch(Dispatchers.Main) {
                if (item != null) {
                    withContext(Dispatchers.IO) {
                        MarvelLogic().favMarvelCharDB(
                            MarvelChars(
                                item.id,
                                item.name,
                                item.comic,
                                item.image,
                                item.desc
                            )
                        )
                    }
                }
            }
        }
        }

    }
