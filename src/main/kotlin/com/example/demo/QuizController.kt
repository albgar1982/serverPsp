package com.example.demo

import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
class QuizController {

    @GetMapping("pregunta")
    fun getQuestion() : String{
        val indice=Random.nextInt(0,Repositorio.preguntasYrespuestas.size)
        return Repositorio.preguntasYrespuestas[indice].pregunta + Repositorio.preguntasYrespuestas[indice].respuestas.toString()

    }

    @GetMapping("pregunta/{pregunta}/{respuesta}")
    fun getQuestion(@PathVariable numPregunta:Int,@PathVariable respuesta:String){}


}