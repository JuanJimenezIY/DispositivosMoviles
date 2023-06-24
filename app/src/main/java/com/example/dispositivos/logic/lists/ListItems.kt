package com.example.dispositivos.logic.lists

import com.example.dispositivos.ui.utilities.MarvelChars

class ListItems

{
    fun returnMarvelChar():List<MarvelChars>{
        val items= listOf(MarvelChars(
            1,
            "Iron Man"
            ,"The invincible Iron Man (1968)"
            ,"https://comicvine.gamespot.com/a/uploads/original/11174/111743204/8923866-ironman.jpg"
            ,"Iron Man (conocido en español como el Hombre de Hierro) es un superhéroe que aparece en los cómics estadounidenses publicados por Marvel Comics, nacido en Sofía, Bulgaria. "

            ),MarvelChars(
                2,
                "Wolverine"
                ,"Wolverine (1989)"
                ,"https://comicvine.gamespot.com/a/uploads/scale_small/5/57023/7469590-wolverinerb.jpg"
                ,"Wolverine, cuyo nombre de nacimiento es James Howlett (también conocido como James Logan o simplemente Logan) es un superhéroe ficticio que aparece en los cómics publicados por Marvel Comics"
            ),MarvelChars(
                3,
                "SpiderMan"
                ,"The amazing spiderman (1962)"
                ,"https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8126579-amazing_spider-man_vol_5_54_stormbreakers_variant_textless.jpg"
                ,"Spider-Man, traducido en ocasiones como el Hombre Araña, es un personaje creado por los estadounidenses Stan Lee y Steve Ditko, e introducido en el cómic Amazing Fantasy n.° 15, publicado por Marvel Comics en agosto de 1962."
            ),MarvelChars(
                4,
                "Capitan America"
                ,"Captain America (1968)"
                ,"https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8459983-rco031_1650495781.jpg"
                ,"El Capitán América, cuyo nombre real es Steven  Grant Rogers, es un superhéroe ficticio que aparece en los cómics estadounidenses publicados por Marvel Comics."
            ),MarvelChars(
                5,
                "Hulk"
                ,"The incredible hulk(1968)"
                ,"https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/7892286-immortal_hulk_vol_1_38_.jpg"
                ,"Hulk (llamado Hulk o El Hombre Increíble en muchas de las traducciones al español) es un personaje ficticio, un superhéroe que aparece en los cómics estadounidenses publicados por la editorial Marvel Comics, es considerado el personaje más fuerte de Marvel Comics."
            ))
        return items
    }
}