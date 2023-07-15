package com.example.dispositivos.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.dispositivos.R
import com.example.dispositivos.databinding.ActivityEmptyBinding
import com.example.dispositivos.ui.fragment.FirstFragment
import com.example.dispositivos.ui.fragment.SecondFragment
import com.example.dispositivos.ui.fragment.ThirdFragment
import com.example.dispositivos.ui.utilities.FragmentsManager
import com.google.android.material.snackbar.Snackbar

class EmptyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE","Entrando a Create")
        binding=ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    override fun onStart() {
        super.onStart()
        /*
        var name:String=""
        intent.extras.let {
            // it.toString()
            name= it?.getString("var2")!!
        }
        Log.d("UCE","Hola${name}")
        binding.textView.text="Bienvenido "+name.toString()
       Log.d("UCE","Entrando a Start")*/

        initClass()
    }
    private fun initClass(){

/*
        binding.button1.setOnClickListener{

            var f = Snackbar.make(
                binding.button1,
                "EmptyActivity",
                Snackbar.LENGTH_SHORT
            )
                .show()


            var intent = Intent(this,MainActivity::class.java
            )
         //   intent.putExtra("var1","Juan")





        }
*/


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.inicio -> {
                    // Respond to navigation item 1 click
               FragmentsManager().replaceFragment(
                   supportFragmentManager,binding.frmContainer.id,FirstFragment()
               )
                    true
                }
                R.id.busqueda -> {
                    // Respond to navigation item 2 click
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,binding.frmContainer.id,SecondFragment()
                    )

                    true
                }
                R.id.favoritos -> {
                    // Respond to navigation item 2 click
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,binding.frmContainer.id,ThirdFragment()
                    )

                    true
                }
                else -> false

            }



        }
        startActivity(intent)
    }

}



