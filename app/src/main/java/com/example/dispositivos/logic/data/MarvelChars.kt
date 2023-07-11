package com.example.dispositivos.logic.data

import android.os.Parcelable
import com.example.dispositivos.data.entities.marvel.characters.data.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(
    val id: Int,
    val name: String,
    val comic: String,
    val image: String,
    val desc: String


) : Parcelable

fun MarvelChars.getMarvelCharsDB(): MarvelCharsDB {

    return MarvelCharsDB(
        id,
        name,
        comic,
        image,
        desc


    )
}

