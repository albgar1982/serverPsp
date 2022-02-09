package com.example.demo

import org.json.JSONObject
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.random.Random

@RestController
class QuizController(private val userRepository: UserRepository) {

    @GetMapping("solicitarPregunta/{token}")
    fun getQuestion(@PathVariable token: String): String {
        val usuario = encontrarUsuario(token)
        val tokenCaducado = checkTokenTime(usuario)
        if (tokenCaducado) {
            println("ERROR")
            return "ERROR"
        } else {
            var indice = Random.nextInt(
                0,
                Repositorio.preguntasYrespuestas.size
            )
            usuario?.let {
                it.preguntas?.let {
                    while(!it.contains(Repositorio.preguntasYrespuestas[indice].identificador))
                        indice = Random.nextInt(0,Repositorio.preguntasYrespuestas.size)
                }
            }

            val enJson = Repositorio.preguntasYrespuestas[indice].toString()
            usuario?.let {
                it.preguntas?.remove(Repositorio.preguntasYrespuestas[indice].identificador)
            }
            val objetoJson = JSONObject(enJson)
            objetoJson.remove("respuestaCorrecta")
            println(objetoJson)
            return objetoJson.toString()
        }
    }

    @GetMapping("responder/{id}/{respuesta}")
    fun answer(@PathVariable id: Int, @PathVariable respuesta: Int): String {
        var aDevolver = "Identificador de pregunta incorrecto"
        var i = 0
        var salir = false
        do {
            if (Repositorio.preguntasYrespuestas[i].identificador == id) {
                salir = true
                aDevolver = if (Repositorio.preguntasYrespuestas[i].respuestaCorrecta == respuesta)
                    "Respuesta correcta"
                else
                    "Respuesta incorrecta"
            } else
                i++
        } while (!salir && i < Repositorio.preguntasYrespuestas.size)

        return aDevolver
    }

    @GetMapping("appendUser/{usuario}/{contrasenia}")
    fun appendUser(@PathVariable usuario: String, @PathVariable contrasenia: String): String {
        var token = crearToken()
        val fecha = Calendar.getInstance()
        val user = User(usuario, contrasenia, token, fecha)
        userRepository.save(user)
        userRepository.findAll().forEach {
            println(it)
        }
        return token
    }

    private fun crearToken(): String {
        var token = ""
        repeat(6) {
            token += Char(Random.nextInt(97, 123))
        }
        return token
    }

    private fun checkTokenTime(usuario: User?): Boolean {
        var caducado = false

        usuario?.let {
            val momentoActual = Calendar.getInstance()
            val segundosActuales = momentoActual.timeInMillis
            val segundosToken = it.fecha.timeInMillis
            val tiempoTranscurrido = segundosActuales - segundosToken
            if (tiempoTranscurrido / (1000 * 60) > 5)
                caducado = true
        }

        return caducado
    }

    private fun encontrarUsuario(token: String): User? {
        var usuario: User? = null
        val listaUsuarios = userRepository.findAll()
        var i = 0
        var salir = false
        do {
            if (listaUsuarios[i].token == token) {
                println("Estoy aquí")
                usuario = listaUsuarios[i]
                salir = true
            } else
                i++
        } while (!salir && i < listaUsuarios.size)
        return usuario
    }
}