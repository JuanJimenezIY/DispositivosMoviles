package com.example.dispositivos.data.entities.marvel.characters.data

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)