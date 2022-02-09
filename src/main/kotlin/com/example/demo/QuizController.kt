package com.example.demo

import org.json.JSONObject
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.random.Random

@RestController
class QuizController(private val userRepository: UserRepository) {

    @GetMapping("solicitarPregunta/{token}")
    fun getQuestion(@PathVariable token: String): String {
        var pregunta = ""
        val usuario = userRepository.getById(encontrarUsuario(token))
        val tokenCaducado = checkTokenTime(usuario)
        if (tokenCaducado) {
            println("ERROR")
            return "ERROR"
        } else {
            if(usuario.idPreguntas.size==0)
                return "No quedan preguntas"
            val numPregunta = usuario.idPreguntas[Random.nextInt(0, usuario.idPreguntas.size)]
            var i = 0
            var salir = false
            do {
                if (Repositorio.preguntasYrespuestas[i].identificador == numPregunta) {
                    salir = true
                    pregunta = Repositorio.preguntasYrespuestas[i].toString()
                    usuario.idPreguntas.remove(numPregunta)
                    userRepository.save(usuario)
                } else
                    i++
            } while (!salir && i < Repositorio.preguntasYrespuestas.size)


            val objetoJson = JSONObject(pregunta)
            objetoJson.remove("respuestaCorrecta")
            println(objetoJson)
            return objetoJson.toString()
        }
    }

    @GetMapping("responder/{id}/{respuesta}/{token}")
    fun answer(@PathVariable id: Int, @PathVariable respuesta: Int, @PathVariable token: String): String {
        val usuario = userRepository.getById(encontrarUsuario(token))
        val tokenCaducado = checkTokenTime(usuario)
        if (tokenCaducado) {
            println("ERROR")
            return "ERROR"
        } else {
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
    }

    @GetMapping("appendUser/{usuario}/{contrasenia}")
    fun appendUser(@PathVariable usuario: String, @PathVariable contrasenia: String): String {
        val token = crearToken()
        val fecha = Calendar.getInstance()
        val posibleUsuario = comprobarUsuarioYcontrasenia(usuario, contrasenia)
        if (posibleUsuario != null) {
            //Ya existe el usuario
            posibleUsuario.token = token
            posibleUsuario.fecha = fecha
            userRepository.save(posibleUsuario)
        } else {
            val user = User(usuario, contrasenia, token, fecha, generaListaIdsPreguntas())
            userRepository.save(user)
        }
        userRepository.findAll().forEach {
            println(it)
        }
        return token
    }

    private fun comprobarUsuarioYcontrasenia(usuario: String, contrasenia: String): User? {
        var user: User? = null
        val listaUsuarios = userRepository.findAll()
        var i = 0
        var salir = false
        do {
            if(listaUsuarios.isEmpty())
                salir = true
            else{
                if (listaUsuarios[i].usuario == usuario && listaUsuarios[i].contrasenia == contrasenia) {
                    user = listaUsuarios[i]
                    salir = true
                } else
                    i++
            }
        } while (!salir && i < listaUsuarios.size)
        return user
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

    private fun encontrarUsuario(token: String): Int {
        var idUsuario = -1
        val listaUsuarios = userRepository.findAll()
        var i = 0
        var salir = false
        do {
            if (listaUsuarios[i].token == token) {
                println("He encontrado el token $token. El del usuario es: ${listaUsuarios[i].token}")
                idUsuario = listaUsuarios[i].id
                salir = true
            } else{
                println("NO he encontrado el token $token. El del usuario es: ${listaUsuarios[i].token}")
                i++
            }

        } while (!salir && i < listaUsuarios.size)
        return idUsuario
    }

    private fun generaListaIdsPreguntas(): ArrayList<Int> {
        val listaPreguntas = arrayListOf<Int>()

        Repositorio.preguntasYrespuestas.forEach {
            listaPreguntas.add(it.identificador)
        }
        return listaPreguntas
    }
}