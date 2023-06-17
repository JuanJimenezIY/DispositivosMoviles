package com.example.dispositivos.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivos.R
import com.example.dispositivos.databinding.FragmentFirstBinding
import com.example.dispositivos.logic.lists.ListItems
import com.example.dispositivos.ui.adapters.MarvelAdapter

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

        binding = FragmentFirstBinding.inflate(layoutInflater
            ,container,
            false)

        return  binding.root
    }

    override fun onStart() {
        super.onStart()
        val names= arrayListOf<String>("Carlos","Juan","Xavier","Andres","Pepe","Antonio")

        val adapter= ArrayAdapter<String>(
            requireActivity(),R.layout.simple_spinner,names)


        binding.spinner.adapter= adapter
       // binding.listView.adapter = adapter

        val rvAdapter= MarvelAdapter(ListItems().returnMarvelChar())
        val rvMarvel =binding.rvMarvelChars
        rvMarvel.adapter=rvAdapter
        rvMarvel.layoutManager= LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false
        )


    }





}

