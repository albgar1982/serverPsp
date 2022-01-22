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
        val sType = object : TypeToken<MutableMap<String,String>>() { }.type // Creo la variable de tipo Type especificando el tipo al que quiero transformar el Json
        val mapa = gson.fromJson<MutableMap<String,String>>(enJson, sType) //Convertimos en un Map el Json
        mapa.remove("respuestaCorrecta") //Quitándole la K y V de "respuestaCorrecta"
        return gson.toJson(mapa) //Devolvemos el Map en Json
    }

    @GetMapping("responder/{id}/{respuesta}")
    fun answer(@PathVariable id:Int,@PathVariable respuesta : String) : String{
        var aDevolver=""

        if (id >=0 && id < Repositorio.preguntasYrespuestas.size)
            if(Repositorio.preguntasYrespuestas[id].respuestaCorrecta==respuesta)
                aDevolver="Respuesta correcta"
            else
                aDevolver="Respuesta incorrecta"
        else
            aDevolver="Numero de pregunta incorrecto"
        return aDevolver

        //Esto funciona mientras se mantenga que los identificadores de las preguntas coincidan con la posición que ocupan en la lista
        //Si no coincidieran, habría que iterar por la lista hasta encontrar el identificador deseado:
        /*
        var aDevolver=""
        var i = 0
        var salir=false
        do {
            if(Repositorio.preguntasYrespuestas[i].identificador==id)
                salir=true
            else
                i++
        }while (!salir && i<Repositorio.preguntasYrespuestas.size)

        if(salir) {
            if (Repositorio.preguntasYrespuestas[i].respuestaCorrecta == respuesta)
                aDevolver = "Respuesta correcta"
            else
                aDevolver = "Respuesta incorrecta"
        }
        else
            aDevolver="Identificador de pregunta incorrecto"

        return aDevolver
        */
    }


}