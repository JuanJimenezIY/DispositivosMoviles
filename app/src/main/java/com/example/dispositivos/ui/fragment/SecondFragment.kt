package com.example.dispositivos.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos.R
import com.example.dispositivos.databinding.FragmentFirstBinding
import com.example.dispositivos.databinding.FragmentSecondBinding
import com.example.dispositivos.logic.data.MarvelChars
import com.example.dispositivos.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivos.logic.marvelLogic.MarvelLogic
import com.example.dispositivos.ui.activities.DetailsMarvelItem
import com.example.dispositivos.ui.adapters.MarvelAdapter
import com.example.dispositivos.ui.adapters.MarvelAdapterFS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gmanager: GridLayoutManager

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    private  var rvAdapter: MarvelAdapterFS = MarvelAdapterFS { sendMarvelItems(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(layoutInflater
            ,container,
            false)
        lmanager =LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )
        gmanager= GridLayoutManager(
            requireActivity(),2
        )
        return  binding.root
    }
    override fun onStart() {
        super.onStart()

        // binding.listView.adapter = adapter
        chargeDataRV()

        binding.rvMarvelChars.addOnScrollListener(
            object  : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if(dy>0){
                        val v= gmanager.childCount
                        val p= gmanager.findFirstVisibleItemPosition()
                        val t= gmanager.itemCount

                        if((v+p)>=t){
                            lifecycleScope.launch((Dispatchers.IO)){
                                val items =JikanAnimeLogic().getAllAnimes()
                                //val items= MarvelLogic().getAllMarvelChars(0,99)
                                /* val newItems = MarvelLogic().getAllCharacters(
                                     name="cap" ,
                                     5)*/
                                withContext(Dispatchers.Main){
                                    rvAdapter.updateListItems(items)
                                }

                            }
                        }
                    }

                }

            })
        binding.txtfilterSecond.addTextChangedListener{filteredText->
            val newItems= marvelCharacterItems.filter {
                    items->
                items.name.lowercase().contains(filteredText.toString().lowercase())

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
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (JikanAnimeLogic().getAllAnimes(

                ))
            } as MutableList<MarvelChars>

            rvAdapter.items =


                    JikanAnimeLogic().getAllAnimes()
                //MarvelLogic().getAllMarvelChars(0, 99)

            //ListItems().returnMarvelChar()
            /*   JikanAnimeLogic().getAllAnimes()
   ) { sendMarvelItems(it) }

*/



            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = gmanager
            }

        }

        }

}