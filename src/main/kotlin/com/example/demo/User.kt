package com.example.demo

import com.google.gson.Gson
import java.util.*
import javax.persistence.*


@Entity
data class User(var usuario:String, var contrasenia:String, var token:String, var fecha:Calendar) {


    @Id
    @GeneratedValue
    var id= 0

    @Column
    @ElementCollection(targetClass = Int::class)
    var preguntas:MutableList<Int>? =generaListaPreguntas()

    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

    private fun generaListaPreguntas(): MutableList<Int>? {
        val listaPreguntas :MutableList<Int>? = null
        Repositorio.preguntasYrespuestas.forEach { pregunta ->
            listaPreguntas?.add(pregunta.identificador)
        }
        return listaPreguntas
    }
}