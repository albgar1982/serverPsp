package com.example.demo

import com.google.gson.Gson

data class Quiz (var identificador : Int,var pregunta: String, var respuesta1 : String, var respuesta2 : String, var respuesta3 : String, var respuesta4 : String,var respuestaCorrecta:Int){
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}