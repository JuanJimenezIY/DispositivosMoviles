package com.example.dispositivos.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos.R
import com.example.dispositivos.data.entities.marvel.characters.MarvelChars
import com.example.dispositivos.databinding.FragmentFirstBinding
import com.example.dispositivos.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivos.logic.lists.ListItems
import com.example.dispositivos.logic.marvelLogic.MarvelLogic
import com.example.dispositivos.ui.activities.DetailsMarvelItem
import com.example.dispositivos.ui.activities.MainActivity
import com.example.dispositivos.ui.adapters.MarvelAdapter

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

    private lateinit var marvelCharacterItems: MutableList<MarvelChars>
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

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV("cap")
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
                                val newItems=JikanAnimeLogic().getAllAnimes()
                               /* val newItems = MarvelLogic().getAllCharacters(
                                    name="cap" ,
                                    5)*/
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
                items.name.contains(filteredText.toString())

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


    fun chargeDataRV(search :String) {


        lifecycleScope.launch(Dispatchers.IO) {

            var marvelCharacterItems=MarvelLogic().getAllCharacters(
                "Spider",5
            )
             rvAdapter.items =


                 JikanAnimeLogic().getAllAnimes()
                 // MarvelLogic().getAllCharacters(name=search ,5)

                 //ListItems().returnMarvelChar()
                     /*   JikanAnimeLogic().getAllAnimes()
            ) { sendMarvelItems(it) }

*/
            withContext(Dispatchers.Main) {


                with(binding.rvMarvelChars){
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }


            }
        }
    }

}

