package com.example.dispositivos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos.R
import com.example.dispositivos.databinding.MarvelCharactersBinding
import com.example.dispositivos.databinding.MarvelCharactersGrindBinding
import com.example.dispositivos.logic.data.MarvelChars
import com.squareup.picasso.Picasso

class MarvelAdapterFS(
    private var fnClick: (MarvelChars) -> Unit
    ) :
    RecyclerView.Adapter<MarvelAdapterFS.MarvelViewHolder>() {

        var items: List<MarvelChars> = listOf()

        class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val binding: MarvelCharactersGrindBinding = MarvelCharactersGrindBinding.bind(view)

            fun render(
                item: MarvelChars,
                fnClick: (MarvelChars) -> Unit
            ) {
                binding.txtNombreP.text = item.name

                Picasso.get().load(item.image).into(binding.imagenMarvel)


                itemView.setOnClickListener {
                    fnClick(item)
                }

                binding.imagenMarvel.setOnClickListener {
                    fnClick(item)

                    /* Snackbar.make(binding.imagenMarvel,
                     item.name,
                     Snackbar.LENGTH_SHORT)
                     .show()*/
                }
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MarvelAdapterFS.MarvelViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return MarvelViewHolder(
                inflater.inflate(
                    R.layout.marvel_characters_grind,
                    parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }



        override fun getItemCount(): Int = items.size

        fun updateListItems(newItems: List<MarvelChars>) {
            this.items = this.items.plus(newItems)
            notifyDataSetChanged()

        }

        fun replaceListAdapter(newItems: List<MarvelChars>) {
            this.items = newItems
            notifyDataSetChanged()

        }
}