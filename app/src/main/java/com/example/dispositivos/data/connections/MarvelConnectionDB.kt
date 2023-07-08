package com.example.dispositivos.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispositivos.data.dao.marvel.MarvelCharsDao
import com.example.dispositivos.data.entities.marvel.characters.data.database.MarvelCharsDB

@Database(
    entities=[MarvelCharsDB::class],
version = 1
)
abstract class MarvelConnectionDB: RoomDatabase() {
    abstract fun marvelDao(): MarvelCharsDao
}