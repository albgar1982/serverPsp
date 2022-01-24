package com.example.demo

import org.json.JSONObject
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
class QuizController {

    @GetMapping("solicitarPregunta")
    fun getQuestion(): String {
        val indice = Random.nextInt(0, Repositorio.preguntasYrespuestas.size) //Cogemos un objeto Quiz aleatorio del repositorio
        val enJson = Repositorio.preguntasYrespuestas[indice].toString() //Devuelve el Json de ese objeto Quiz
        val objetoJson = JSONObject(enJson)
        objetoJson.remove("respuestaCorrecta")
        return objetoJson.toString()
    }

    @GetMapping("responder/{id}/{respuesta}")
    fun answer(@PathVariable id:Int,@PathVariable respuesta : Int) : String{
        var aDevolver="Identificador de pregunta incorrecto"
        var i = 0
        var salir=false
        do {
            if(Repositorio.preguntasYrespuestas[i].identificador==id) {
                salir = true
                aDevolver = if (Repositorio.preguntasYrespuestas[i].respuestaCorrecta == respuesta)
                    "Respuesta correcta"
                else
                    "Respuesta incorrecta"
            }
            else
                i++
        }while (!salir && i<Repositorio.preguntasYrespuestas.size)

        return aDevolver
    }


}