package com.example.dispositivos.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import com.example.dispositivos.R
import com.example.dispositivos.databinding.FragmentFirstBinding

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
    }





}

