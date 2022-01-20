package com.example.demo

import com.google.gson.Gson

data class Quiz (var pregunta: String, var respuestas : MutableList<String>,var respuestaCorrecta:Int){
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}