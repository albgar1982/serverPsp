package com.example.demo

import com.google.gson.Gson
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
data class User(var usuario:String, var contrasenia:String, var token:String, var fecha:Calendar,var idPreguntas: ArrayList<Int>) {

    @Id
    @GeneratedValue
    var id= 0

    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

}