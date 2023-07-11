package com.example.dispositivos.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.dispositivos.data.connections.MarvelConnectionDB
import com.example.dispositivos.data.entities.marvel.characters.data.database.MarvelCharsDB
import com.example.dispositivos.logic.data.MarvelChars

class Dispositivos : Application(){


    val name_class : String = "Admin"
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB"
                ).build()


    }

    companion object {

       private  var db : MarvelConnectionDB? = null
        fun getDbInstance() : MarvelConnectionDB{
            return db!!
        }
    }
}