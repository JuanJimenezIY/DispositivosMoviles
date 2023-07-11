package com.example.dispositivos.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos.R
import com.example.dispositivos.logic.data.MarvelChars
import com.example.dispositivos.databinding.FragmentFirstBinding
import com.example.dispositivos.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivos.logic.marvelLogic.MarvelLogic
import com.example.dispositivos.ui.activities.DetailsMarvelItem
import com.example.dispositivos.ui.adapters.MarvelAdapter
import com.example.dispositivos.ui.utilities.Dispositivos

import kotlinx.coroutines.Dispatchers
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

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

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
        val names = arrayListOf<String>("Carlos", "Juan", "Xavier", "Andres", "Pepe", "Antonio")

        val adapter = ArrayAdapter<String>(
            requireActivity(), R.layout.simple_spinner, names
        )


        binding.spinner.adapter = adapter
        // binding.listView.adapter = adapter
        chargeDataRVDB()
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVDB()
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
                            lifecycleScope.launch((Dispatchers.IO)){
                                //val items=MarvelLogic().getAllMarvelChars(0,99)
                                val newItems = MarvelLogic().getAllCharacters(
                                    name="spider" ,
                                    10)
                                withContext(Dispatchers.Main){
                                    rvAdapter.updateListItems(newItems)
                                }

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



    private fun chargeDataRV() {
        lifecycleScope.launch(Dispatchers.Main) {
             marvelCharacterItems= withContext(Dispatchers.IO){
                 return@withContext (MarvelLogic().getAllCharacters (name="spider" ,
                     10
                 ))
             } as MutableList<MarvelChars>
             rvAdapter.items =
                 //JikanAnimeLogic().getAllAnimes()
                  MarvelLogic().getAllCharacters(name="spider" ,
                      10)
                 //ListItems().returnMarvelChar()
                     /*   JikanAnimeLogic().getAllAnimes()
            ) { sendMarvelItems(it) }

*/           binding.rvMarvelChars.apply{
                    this.adapter = rvAdapter
                  //  this.layoutManager = lmanager
                    this.layoutManager = lmanager
                }



        }
    }

    private fun chargeDataRVDB() {
        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems= withContext(Dispatchers.IO){
                var marvelCharsItems=
                    MarvelLogic().getAllCharactersDB().toMutableList()


            if (marvelCharsItems.isEmpty()) {
                marvelCharacterItems = (MarvelLogic().getAllCharacters(
                        name = "spider",
                        10
                    ).toMutableList())
                MarvelLogic().insertMarvelCharstoDB(marvelCharacterItems)
                }
                return@withContext marvelCharsItems
            }



            rvAdapter.items =marvelCharacterItems
                    //JikanAnimeLogic().getAllAnimes()
               // MarvelLogic().getAllCharactersDB()
            //ListItems().returnMarvelChar()
            /*   JikanAnimeLogic().getAllAnimes()
   ) { sendMarvelItems(it) }

*/           binding.rvMarvelChars.apply{
            this.adapter = rvAdapter
            //  this.layoutManager = lmanager
            this.layoutManager = lmanager
        }



        }
    }

}

