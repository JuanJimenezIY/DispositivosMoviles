package com.example.dispositivos.logic.marvelLogic

import android.util.Log
import com.example.dispositivos.data.connections.ApiConnection
import com.example.dispositivos.data.endpoints.MarvelEndpoint
import com.example.dispositivos.data.entities.marvel.characters.data.getMarvelChars
import com.example.dispositivos.logic.data.MarvelChars

class MarvelLogic {
    suspend fun getAllCharacters(name:String,limit:Int):List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var call=
            ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndpoint::class.java)
        if (call!=null){
            var response=call.getCharactersStartsWith(name,limit)

            if (response.isSuccessful){
                response.body()!!.data.results.forEach{
                    val m= it.getMarvelChars()
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

    suspend fun getAllMarvelChars(offset:Int,limit:Int):List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var call=
            ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndpoint::class.java)
        if (call!=null){
            var response=call.getAllMarvelChars(offset,limit)

            offset.toString()

            if (response.isSuccessful){
                response.body()!!.data.results.forEach{
                   val m= it.getMarvelChars()
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

