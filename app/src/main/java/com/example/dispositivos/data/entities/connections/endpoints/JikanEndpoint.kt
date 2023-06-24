package com.example.dispositivos.data.entities.connections.endpoints

import com.example.dispositivos.data.entities.jikan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndpoint  {
    @GET("top/anime")
    suspend fun getAllAnimes():Response<JikanAnimeEntity>


}