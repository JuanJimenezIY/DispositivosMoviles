package com.example.dispositivos.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivos.R
import com.example.dispositivos.databinding.FragmentFirstBinding
import com.example.dispositivos.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivos.logic.lists.ListItems
import com.example.dispositivos.ui.activities.DetailsMarvelItem
import com.example.dispositivos.ui.activities.MainActivity
import com.example.dispositivos.ui.adapters.MarvelAdapter
import com.example.dispositivos.ui.utilities.MarvelChars
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(
            layoutInflater, container,
            false
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
            chargeDataRV()
            binding.rvSwipe.isRefreshing = false
        }


    }

    //se va a aenviar como parametro en el adaptador

    fun sendMarvelItems(item: MarvelChars) {

        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }


    fun chargeDataRV() {

        lifecycleScope.launch(Dispatchers.IO) {
            val rvAdapter = MarvelAdapter(

                JikanAnimeLogic().getAllAnimes()
                // ListItems().returnMarvelChar()

            ) { sendMarvelItems(it) }


            withContext(Dispatchers.Main) {

                with(binding.rvMarvelChars){
                    this.adapter = rvAdapter
                    this.layoutManager = LinearLayoutManager(
                        requireActivity(), LinearLayoutManager.VERTICAL, false
                    )
                }


            }
        }
    }

}

