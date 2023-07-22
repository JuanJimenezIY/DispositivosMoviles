package com.example.dispositivos.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos.R
import com.example.dispositivos.data.entities.marvel.characters.data.database.MarvelCharsDB
import com.example.dispositivos.logic.data.MarvelChars
import com.example.dispositivos.databinding.FragmentFirstBinding
import com.example.dispositivos.logic.data.getMarvelCharsDB
import com.example.dispositivos.logic.marvelLogic.MarvelLogic
import com.example.dispositivos.ui.activities.DetailsMarvelItem
import com.example.dispositivos.ui.activities.dataStore
import com.example.dispositivos.ui.adapters.MarvelAdapter
import com.example.dispositivos.ui.data.UserDataStore
import com.example.dispositivos.ui.utilities.Dispositivos
import com.example.dispositivos.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gmanager: GridLayoutManager
    private val limit =99
    private var offset=0

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf()

    private  var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItems(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(
            layoutInflater, container,
            false
        )
        lmanager =LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )
        gmanager= GridLayoutManager(
            requireActivity(),2
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch(Dispatchers.Main){
            getDataStore().collect(){
                user->
                Log.d("UCE",user.name)
                Log.d("UCE",user.email)

                Log.d("UCE",user.session)
            }
        }

        val names = arrayListOf<String>("Carlos", "Juan", "Xavier", "Andres", "Pepe", "Antonio")

        val adapter = ArrayAdapter<String>(
            requireActivity(), R.layout.simple_spinner, names
        )


        binding.spinner.adapter = adapter
        // binding.listView.adapter = adapter

      // chargeDataRVDB(limit,offset)
       // chargeDataRV(limit,offset)
        binding.rvSwipe.setOnRefreshListener {
          //  chargeDataRVDB(limit,offset)
          chargeDataRV(limit,offset)
            //chargeDataRVDB(offset,limit)
            binding.rvSwipe.isRefreshing = false
        }
        binding.rvMarvelChars.addOnScrollListener(
            object  : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if(dy>0){
                        val v= lmanager.childCount
                        val p= lmanager.findFirstVisibleItemPosition()
                        val t= lmanager.itemCount

                        if((v+p)>=t){
                            lifecycleScope.launch((Dispatchers.Main)){
                                //val items=MarvelLogic().getAllMarvelChars(0,99)
                                val newItems = with(Dispatchers.IO){
                                    MarvelLogic().getAllMarvelChars(offset ,
                                        limit)


                                }
                                    rvAdapter.updateListItems(newItems)
                                    this@FirstFragment.offset+=offset


                            }
                        }
                    }

                }

        })
        binding.txtfilter.addTextChangedListener{filteredText->
            val newItems= marvelCharacterItems.filter {
                items->
                items.name.lowercase(). contains(filteredText.toString().lowercase())

            }

            rvAdapter.replaceListAdapter(newItems)
        }

    }

    //se va a aenviar como parametro en el adaptador

    fun sendMarvelItems(item: MarvelChars) {

        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

//11/07/2023
    /*
    fun saveMarvelItems(item: MarvelChars):Boolean {

        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                Dispositivos.getDbInstance()
                    .marvelDao()
                    .insertMarvelChar(listOf(item.getMarvelCharsDB()))
            }
        }
return true
    }
    */



    private fun chargeDataRV(limit: Int,offset: Int) {

        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharacterItems = withContext(Dispatchers.IO) {
                    return@withContext (MarvelLogic().getAllMarvelChars(
                        offset ,
                        limit
                    ) as MutableList<MarvelChars>)
                }
                Log.d("DATOS",marvelCharacterItems.size.toString())
                rvAdapter.items = marvelCharacterItems
                //JikanAnimeLogic().getAllAnimes()

                //ListItems().returnMarvelChar()
                /*   JikanAnimeLogic().getAllAnimes()
            ) { sendMarvelItems(it) }
*/           binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager

            }
                this@FirstFragment.offset =offset+ limit
            }
        }else{
            Snackbar.make(
                binding.card,
                "No hay conexión",
                Snackbar.LENGTH_LONG
            ).show()
        }

    }

    private fun chargeDataRVDB(limit: Int,offset: Int) {

        if (Metodos().isOnline(requireActivity())) {

        lifecycleScope.launch(Dispatchers.Main) {
            Log.d("DATOSNADA",marvelCharacterItems.size.toString())

            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllCharsDB(offset,limit

                ))
            }
            Log.d("DATOS",marvelCharacterItems.size.toString())


            rvAdapter.items = marvelCharacterItems
            //JikanAnimeLogic().getAllAnimes()
            // MarvelLogic().getAllCharactersDB()
            //ListItems().returnMarvelChar()
            /*   JikanAnimeLogic().getAllAnimes()
   ) { sendMarvelItems(it) }

*/           binding.rvMarvelChars.apply {
            this.adapter = rvAdapter
              //this.layoutManager = gmanager
            this.layoutManager = lmanager
        }
            this@FirstFragment.offset+=limit
        }
        }else{
            Snackbar.make(
                binding.card,
                "No hay conexión",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }


    private fun getDataStore()=
        requireActivity().dataStore.data.map {

                prefs ->
            UserDataStore(
                name = prefs[stringPreferencesKey("usuario")].orEmpty(),
                email = prefs[stringPreferencesKey("email")].orEmpty(),
                session = prefs[stringPreferencesKey("session")].orEmpty()
            )

    }
}




