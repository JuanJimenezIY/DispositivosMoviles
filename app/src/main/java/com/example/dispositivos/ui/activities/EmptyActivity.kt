package com.example.dispositivos.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivos.R
import com.example.dispositivos.databinding.ActivityEmptyBinding
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
        var name:String=""
//        intent.extras.let {
//            // it.toString()
//             name= it?.getString("var2")!!
//        }
        Log.d("UCE","Hola${name}")
        binding.textView.text="Bienvenido "+name.toString()
        Log.d("UCE","Entrando a Start")

        initClass()
    }
    private fun initClass(){
        binding.button1.setOnClickListener{
            /*
            var f = Snackbar.make(
                binding.button1,
                "EmptyActivity",
                Snackbar.LENGTH_SHORT
            )
                .show()
                */

            var intent = Intent(this,MainActivity::class.java
            )
         //   intent.putExtra("var1","Juan")





        }

        binding.bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when(item.itemId) {
                R.id.inicio -> {
                    // Respond to navigation item 1 click
                    var suma: Int =0
                    for(i in listOf(4,8,21)){
                        suma=suma+i
                    }
                    Snackbar.make(
                        binding.bottomNavigation
                        ,"La suma es ${suma}",
                        Snackbar.LENGTH_LONG
                    ).show()
                    true
                }
                R.id.favoritos -> {
                    // Respond to navigation item 2 click
                    var suma: Int =0
                    for(i in listOf(1,2,3)){
                        suma=suma+i
                    }
                    Snackbar.make(
                        binding.bottomNavigation
                        ,"La suma es ${suma}",
                        Snackbar.LENGTH_LONG
                    ).show()
                    true
                }
                R.id.apis -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false

            }
            startActivity(intent)
        }}
}