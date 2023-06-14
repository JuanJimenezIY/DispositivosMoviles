package com.example.dispositivos.ui.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos.R
import com.example.dispositivos.databinding.MarvelCharactersBinding
import com.example.dispositivos.ui.utilities.MarvelChars

class MarvelAdapter(private val items: List<MarvelChars>):RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    class MarvelViewHolder(view: View) :RecyclerView.ViewHolder(view){
        private val binding:MarvelCharactersBinding= MarvelCharactersBinding.bind(view)

         fun render(item:MarvelChars){
             binding.txtTitulo.text = item.name
             binding.txtComic.text=item.comic
         }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(
            R.layout.marvel_characters,
            parent,false
        ))
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position])
    }

    override fun getItemCount(): Int =items.size

}