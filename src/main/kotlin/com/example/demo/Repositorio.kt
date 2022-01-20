package com.example.demo

class Repositorio {
    companion object{
        var preguntasYrespuestas= mutableListOf(
            Quiz("Capital de España?", mutableListOf("Madrid","Roma","Londres"),0),
            Quiz("Capital de Reino Unido?", mutableListOf("Madrid","Roma","Londres"),2),
            Quiz("Capital de Italia?", mutableListOf("Madrid","Roma","Londres"),1)
        )
    }
}