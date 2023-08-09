package com.example.dispositivos.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ContentProviderClient

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker.PermissionResult
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
import com.example.dispositivos.ui.utilities.MyLocationManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var client:SettingsClient

    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private var currentLocation: Location?=null
    //firebase
    private lateinit var auth: FirebaseAuth


    @SuppressLint("MissingPermission")
    val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {

                true -> {

                    client.checkLocationSettings(locationSettingsRequest).apply {

                        addOnSuccessListener {
                            val task = fusedLocationProviderClient.lastLocation
                            task.addOnSuccessListener {
                            location->

                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.getMainLooper()
                            )
                            }
                        }
                        addOnFailureListener{ex->

                            if (ex is ResolvableApiException){
                              //  startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                ex.startResolutionForResult(
                                    this@MainActivity,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }

                        }


                    }

                }
/*
                    task.addOnSuccessListener { location ->
                        val alert= AlertDialog.Builder(this).apply {
                            setTitle("Notificacion")
                            setMessage("Pro favor verificque si el gps esta activo")
                            setPositiveButton("Verificar"){
                                dialog,id->
                                val i =Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                startActivity(i)
                                dialog.dismiss()
                            }
                            setCancelable(false)
                        }.show()
                        alert.apply {
                            setTitle("Alerta")
                            setMessage("Existe un problema con el sistema de posicionamiento global en el sistema")
                            setPositiveButton("Okey"){
                                    dialog,id->
                                dialog.dismiss()
                            }
                            setNegativeButton("Cancelar"){
                                    dialog,id->
                                dialog.dismiss()
                            }
                            setCancelable(false)
                            //setNegativeButton("")
                        }.create()
                        alert.show()
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )


                        it.longitude
                        it.latitude
                        val a=Geocoder(this)
                        a.getFromLocation(it.latitude,it.longitude,1)
                    }

                    task.addOnFailureListener{ex->
                        if(ex is ResolvableApiException){
                            ex.startResolutionForResult(
                                this@MainActivity,
                                LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                            )
                        }

                    }

                    val task=fusedLocationProviderClient.lastLocation

                    task.addOnSuccessListener {

                        if (task.result != null) {
                            Snackbar.make(
                                binding.txtTitulo,
                                "${it.latitude},${it.longitude}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }*/


                shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    //Snackbar.make(binding.txtTitulo,"Encienda el GPS porfis",Snackbar.LENGTH_LONG).show()
                }

                false -> {
                     Snackbar.make(binding.txtTitulo,"denegado",Snackbar.LENGTH_LONG).show()
                }
            }
        }

    val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(binding.txtName, "", Snackbar.LENGTH_LONG)
            var message = ""

            when (activityResult.resultCode) {
                RESULT_OK -> {

                    message = "proceso exitoso"
                    sn.setBackgroundTint(
                        resources.getColor(
                            R.color.black
                        )
                    )
                    val msg =
                        activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            ?.get(0).toString()

                    if (msg.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )

                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        Log.d("UCE", msg)
                        intent.putExtra(
                            SearchManager.QUERY, msg
                        )
                        startActivity(intent)
                    }

                }

                RESULT_CANCELED -> {

                    message = "proceso cancelado"
                    sn.setBackgroundTint(
                        resources.getColor(
                            R.color.black
                        )
                    )

                }

                else -> {
                    message = "proceso dudoso"

                    sn.setBackgroundTint(
                        resources.getColor(
                            R.color.fondo_marvel
                        )
                    )
                }
            }

            sn.setText(message)
            sn.show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //firebase
        auth = Firebase.auth
        binding.registro.setOnClickListener {
            authWithFirebaseEmail(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )
        }
        binding.btnLogin.setOnClickListener {
            signWithFirebaseEmail(
                binding.txtName.text.toString(),
                binding.txtPassword.text.toString()
            )
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000
        ).build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                if (locationResult != null) {
                    locationResult.locations.forEach { location ->
                        currentLocation=location
                        Log.d(
                            "UCE",
                            "ubicacion: ${location.latitude}," +
                                    "${location.longitude}"
                        )
                    }

                }
            }
        }
        client=LocationServices.getSettingsClient(this)
        locationSettingsRequest=LocationSettingsRequest.Builder()
            .addLocationRequest(
                locationRequest).build()

    }

    private fun authWithFirebaseEmail(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constants.TAG, "createUserWithEmail:success")

                    Toast.makeText(
                        baseContext,
                        "Authentication success.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    val user =auth.currentUser



                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constants.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }



    }
    private fun signWithFirebaseEmail(email:String, password:String) {
        auth.signInWithEmailAndPassword (email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constants.TAG, "singInrWithEmail:success")
                    val user =auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication success.",
                        Toast.LENGTH_SHORT,
                    ).show()

                    startActivity(Intent(this,EmptyActivity::class.java))


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constants.TAG, "singInrWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }



    }

    private fun recoveryPasswordEmail(email:String){
        auth.sendPasswordResetEmail(email).addOnCompleteListener{
            task->
            if (task.isSuccessful){
                Toast.makeText(
                    baseContext,
                    "Correo de recuperacion enviado correctamente",
                    Toast.LENGTH_SHORT,
                ).show()

                MaterialAlertDialogBuilder(this).apply {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()


        initClass()


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient
            .removeLocationUpdates(locationCallback)
    }
    @SuppressLint("MissingPermission")
    private fun initClass() {
   /*     binding.btnLogin.setOnClickListener {
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
                    Log.d("UCE", "fallo")
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

*/

        binding.twitter.setOnClickListener {
            locationContract.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            /*
                        var intent = Intent(
                            Intent.ACTION_VIEW,
                            //Uri.parse("https://twitter.com/home?lang=es")
                           // Uri.parse("geo: -0.1990867,-78.5102718")
                            Uri.parse("tel:0998762596")

                        )*/
            /*
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
*/
            //22/07/2023

        }

        val appResultLocal =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->


                val sn = Snackbar.make(binding.txtName, "", Snackbar.LENGTH_LONG)
                var message = ""
                when (resultActivity.resultCode) {
                    RESULT_OK -> {

                        message = resultActivity.data?.getStringExtra("result").orEmpty()
                        sn.setBackgroundTint(
                            resources.getColor(
                                R.color.fondo_marvel
                            )
                        )

                    }

                    RESULT_CANCELED -> {

                        message = "Resultado fallido"
                        sn.setBackgroundTint(
                            resources.getColor(
                                R.color.black
                            )
                        )

                    }

                    else -> {
                        "Dudoso"
                    }
                }
                sn.setText(message)


                sn.show()
            }




        binding.facebook.setOnClickListener {
            // val resIntent = Intent(this, ResultActivity::class.java)
            // appResultLocal.launch(resIntent)

            val intentSpech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intentSpech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intentSpech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")

            speechToText.launch(intentSpech)
        }
    }


    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->


            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dispositivos@gmail"

        }
    }

    private fun test(){
        var location= MyLocationManager(this)

        location.getUserLocation()
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


