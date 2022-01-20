package com.example.demo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
class QuizController {

    @GetMapping("solicitarPregunta")
    fun getQuestion() : String {
        val indice=Random.nextInt(0,Repositorio.preguntasYrespuestas.size) //Cogemos un objeto Quiz aleatorio del repositorio
        val enJson = Repositorio.preguntasYrespuestas[indice].toString() //Devuelve el Json de ese objeto Quiz
        val gson = Gson()
        val sType = object : TypeToken<MutableMap<String,Any>>() { }.type
        val mapa = gson.fromJson<MutableMap<String,Any>>(enJson, sType) //Convertimos en un Map el Json
        mapa.remove("respuestaCorrecta") //Quitándole la K y V de "respuestaCorrecta"
        return gson.toJson(mapa) //Devolvemos el Map en Json
    }

    @GetMapping("responder/{id}/{respuesta}")
    fun answer(@PathVariable id:Int,@PathVariable respuesta : String) : Boolean{
        var i = 0
        var salir=false
        do {
            if(Repositorio.preguntasYrespuestas[i].identificador==id)
                salir=true
            else
                i++
        }while (!salir && i<Repositorio.preguntasYrespuestas.size)

        return Repositorio.preguntasYrespuestas[i].respuestaCorrecta==respuesta
    }


}