package com.example.dispositivos.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivos.databinding.ActivityMainBinding
import com.example.dispositivos.logic.validator.LoginValidator
import com.example.dispositivos.ui.utilities.Dispositivos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onStart() {
        super.onStart()
        initClass()


    }
    override fun onDestroy() {
        super.onDestroy()
    }
    private fun initClass() {
        binding.btnLogin.setOnClickListener {
            //obtenemos la instancia de la clase
            //val loginVal =LoginValidator()
            //acceod al metodo y le vincio los dos parametros que necsito
            val check = LoginValidator().checkLogin(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )


            if (check) {

                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.txtName.text.toString())
                }

                //Intents
                var intent = Intent(
                    this, EmptyActivity::class.java
                )
                //intent.putExtra("var1", "UCE")
               // intent.putExtra("var2", binding.txtName.text.toString())
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.d("UCE","fallo")
                }

            } else {
                Snackbar.make(
                    binding.txtTitulo, "Usuario y contraseña incorrectos",
                    Snackbar.LENGTH_LONG
                ).show()
            }



            /*

            if(binding.txtName.text.toString()=="admin" && binding.txtName.text.toString()=="admin"){

                //Intents
                var intent =Intent(this,EmptyActivity::class.java
                )
                intent.putExtra("var1","UCE")
                intent.putExtra("var2",binding.txtName.text.toString())
                startActivity(intent)

            }else
            {
                Snackbar.make(
                    binding.editTextText
                    ,"Usuario y contraseña invalidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
*/
            // binding.txtBuscar.text = "El codigo se ejecuta correctamente"

            //SUMA
            /*
            var f = Snackbar.make(
                binding.boton1,
                "Este es otro mensaje",
                Snackbar.LENGTH_SHORT
            )
                .show()*/

        }
        binding.twitter.setOnClickListener{
/*
            var intent = Intent(
                Intent.ACTION_VIEW,
                //Uri.parse("https://twitter.com/home?lang=es")
               // Uri.parse("geo: -0.1990867,-78.5102718")
                Uri.parse("tel:0998762596")

            )*/
            val intent = Intent(
                Intent.ACTION_WEB_SEARCH
            )
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intent.putExtra(
                SearchManager.QUERY,"UCE")
            startActivity(intent)
        }

        val appResultLocal= registerForActivityResult(StartActivityForResult()){
            resultActivity->
            when(resultActivity.resultCode){
                RESULT_OK->{Log.d("UCE", "Resultado exitoso")
                    Snackbar.make(binding.txtName,"Resultado exitoso",Snackbar.LENGTH_LONG).show()

                }
                RESULT_CANCELED->{Log.d("UCE", "Resultado fallido")

                    Snackbar.make(binding.txtName,"Resultado fallido",Snackbar.LENGTH_LONG).show()
                }
                else ->{Log.d("UCE", "Resultado dudoso")}
            }

        }
        binding.facebook.setOnClickListener{
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)


        }
    }


    private suspend fun  saveDataStore(stringData: String){
        dataStore.edit {prefs->


            prefs[stringPreferencesKey("usuario")] =   stringData
            prefs[stringPreferencesKey("session")] =   UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] =     "dispositivos@gmail"

        }
    }

    /*
    setContentView(R.layout.activity_main)
    var boton1 = findViewById<TextView>(R.id.boton1)
    var txt_buscar = findViewById<TextView>(R.id.txt_buscar)
    boton1.text = "INGRESAR"

    boton1.setOnClickListener {
        txt_buscar.text = "El evento se ha ejecutado"
        Toast.makeText(
            this,
            "Este es un ejemplo",
            Toast.LENGTH_SHORT
        )
            .show()

        var f=Snackbar.make(boton1,"Este es otro mensaje",  Snackbar.LENGTH_SHORT).show()



}*/

}


