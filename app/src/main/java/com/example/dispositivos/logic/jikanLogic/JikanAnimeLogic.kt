package com.example.dispositivos.logic.jikanLogic

import com.example.dispositivos.data.entities.connections.ApiConnection
import com.example.dispositivos.data.entities.connections.endpoints.JikanEndpoint
import com.example.dispositivos.ui.utilities.MarvelChars

class JikanAnimeLogic {
    suspend fun getAllAnimes():List<MarvelChars>{

        var call= ApiConnection.getJikanConnection()
        val response =call.create(JikanEndpoint::class.java).getAllAnimes()

        var itemList = arrayListOf<MarvelChars>()
        if (response.isSuccessful){
            response.body()!!.data.forEach {
                val m= MarvelChars(it.mal_id,

                    it.title,
                    it.titles[0].title,
                    it.rating,
                    it.images.jpg.image_url

                )
            itemList.add(m)
            }
        }
return itemList
    }
}