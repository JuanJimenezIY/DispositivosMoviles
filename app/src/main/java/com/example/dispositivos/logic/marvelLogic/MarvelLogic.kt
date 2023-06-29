package com.example.dispositivos.logic.marvelLogic

import android.util.Log
import com.example.dispositivos.data.connections.ApiConnection
import com.example.dispositivos.data.endpoints.MarvelEndpoint
import com.example.dispositivos.data.entities.marvel.characters.MarvelChars

class MarvelLogic {
    suspend fun getAllCharacters(name:String,limit:Int):List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var call=
            ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndpoint::class.java)
        if (call!=null){
            var response=call.getCharactersStartsWith(name,limit)

            if (response.isSuccessful){
                response.body()!!.data.results.forEach{
                    var comic:String=""
                    if(it.comics.items.size>0){
                        comic=it.comics.items[0].name
                    }
                    val m= MarvelChars(
                        it.id,
                        it.name,
                        comic,
                        it.thumbnail.path+"."+it.thumbnail.extension,
                        it.description


                    )
                    itemList.add(m)
                }
            }
            else{
                Log.d("UCE",response.toString())
            }
        }


        //Compruebo si la respuesta se ejecuto

        return itemList
    }
}

