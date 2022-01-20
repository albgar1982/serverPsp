package com.example.demo

import com.google.gson.Gson

data class Quiz (var identificador : Int,var pregunta: String, var respuestas : List<String>,var respuestaCorrecta:String){
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}