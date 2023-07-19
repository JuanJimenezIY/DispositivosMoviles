package com.example.dispositivos.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivos.R
import com.example.dispositivos.databinding.ActivityMainBinding
import com.example.dispositivos.logic.validator.LoginValidator
import com.example.dispositivos.ui.utilities.Dispositivos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import java.util.Locale
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


            val sn =Snackbar.make(binding.txtName,"",Snackbar.LENGTH_LONG)
            var message=""
            when(resultActivity.resultCode){
                RESULT_OK->{

                    message =resultActivity.data?.getStringExtra("result").orEmpty()
                    sn.setBackgroundTint(resources.getColor(
                        R.color.fondo_marvel
                    ))

                }
                RESULT_CANCELED->{

                    message="Resultado fallido"
                    sn.setBackgroundTint(resources.getColor(
                        R.color.black
                    ))

                }
                else ->{
                    "Dudoso"
                }
            }
            sn.setText(message)


            sn.show()
        }


        val speechToText = registerForActivityResult(StartActivityForResult()){activityResult ->
            val sn =Snackbar.make(binding.txtName,"",Snackbar.LENGTH_LONG)
            var message=""

            when(activityResult.resultCode){
                RESULT_OK->{

                    message="proceso exitoso"
                    sn.setBackgroundTint(resources.getColor(
                        R.color.black
                    ))
                    val msg = activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0).toString()

                    if (msg.isNotEmpty()){
                        val intent =Intent(Intent.ACTION_WEB_SEARCH
                        )

                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        Log.d("UCE",msg)
                        intent.putExtra(
                            SearchManager.QUERY,msg)
                        startActivity(intent)
                    }

                }
                RESULT_CANCELED->{

                    message="proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(
                        R.color.black
                    ))

                }
                else ->{
                    message="proceso dudoso"

                    sn.setBackgroundTint(resources.getColor(
                        R.color.fondo_marvel
                    ))
                }
            }

            sn.setText(message)
            sn.show()
        }

        binding.facebook.setOnClickListener{
           // val resIntent = Intent(this, ResultActivity::class.java)
            // appResultLocal.launch(resIntent)
            val intentSpech =Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intentSpech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intentSpech.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault())
            intentSpech.putExtra(RecognizerIntent.EXTRA_PROMPT,"Di algo")

            speechToText.launch(intentSpech)
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


