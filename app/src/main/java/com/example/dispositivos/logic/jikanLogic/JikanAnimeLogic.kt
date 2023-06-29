package com.example.dispositivos.logic.jikanLogic

import com.example.dispositivos.data.connections.ApiConnection
import com.example.dispositivos.data.endpoints.JikanEndpoint
import com.example.dispositivos.data.entities.marvel.characters.MarvelChars


class JikanAnimeLogic {
    suspend fun getAllAnimes():List<MarvelChars>{

        var itemList = arrayListOf<MarvelChars>()

        var response= ApiConnection.getService(
            ApiConnection.TypeApi.Jikan,
            JikanEndpoint::class.java
        ).getAllAnimes()




        if (response.isSuccessful){
            response.body()!!.data.forEach {
                val m= MarvelChars(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url,
                    it.rating



                )
            itemList.add(m)
            }
        }
return itemList
    }
}