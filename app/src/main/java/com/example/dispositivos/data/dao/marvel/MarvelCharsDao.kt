package com.example.dispositivos.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivos.data.entities.marvel.characters.data.database.MarvelCharsDB

@Dao
interface MarvelCharsDao {

    @Query("select * from MarvelCharsDB")
    fun getAllCharacters() : List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id=:id")
    fun getOneCharacters(id: Int) : MarvelCharsDB

    @Insert
    fun insertMarvelChar(ch : List<MarvelCharsDB>)


}