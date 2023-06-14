package com.example.dispositivos.logic.lists

import com.example.dispositivos.ui.utilities.MarvelChars

class Listitems

{
    fun returnMarvelChar():List<MarvelChars>{
        val items= listOf(MarvelChars(
            1,
            "Iron Man"
            ,"The invincible Iron Man"
            ,"https://comicvine.gamespot.com/a/uploads/original/11174/111743204/8923866-ironman.jpg"
        ),
            MarvelChars(
                1,
                "Iron Man"
                ,"The invincible Iron Man"
                ,"https://comicvine.gamespot.com/a/uploads/original/11174/111743204/8923866-ironman.jpg"
            ),MarvelChars(
                2,
                "Wolverine"
                ,"Wolverine (1989)"
                ,"https://comicvine.gamespot.com/a/uploads/scale_small/5/57023/7469590-wolverinerb.jpg"
            ),MarvelChars(
                3,
                "SpiderMan"
                ,"The amazing spiderman (1963)"
                ,"https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8126579-amazing_spider-man_vol_5_54_stormbreakers_variant_textless.jpg"
            ),MarvelChars(
                4,
                "Capitan America"
                ,"Captain America (1968)"
                ,"https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8459983-rco031_1650495781.jpg"
            ),MarvelChars(
                5,
                "Hulk"
                ,"The incredible hulk(1968)"
                ,"https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/7892286-immortal_hulk_vol_1_38_.jpg"
            ))
return items
    }
}