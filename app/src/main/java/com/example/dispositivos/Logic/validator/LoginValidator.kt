package com.example.dispositivos.Logic.validator

import com.example.dispositivos.data.entities.LoginUser

class LoginValidator {
    fun checkLogin(name:String ,password:String):Boolean{

        val admin= LoginUser()

        return(admin.name==name
                && admin.pass==password)

    }

}