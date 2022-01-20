package com.example.demo

class Repositorio {
    companion object{
        var preguntasYrespuestas= mutableListOf(
            Quiz(1,"Capital de España?", mutableListOf("Madrid","Roma","Londres"),"Madrid"),
            Quiz(2,"Capital de Reino Unido?", mutableListOf("Madrid","Canberra","Londres"),"Londres"),
            Quiz(3,"Capital de Italia?", mutableListOf("Madrid","Roma","Nom_Pen"),"Roma"),
            Quiz(4,"Capital de Alemania?", mutableListOf("Berlin","Roma","Bogota"),"Berlin"),
            Quiz(5,"Capital de Belgica?", mutableListOf("Bruselas","Roma","Londres"),"Bruselas"),
            Quiz(6,"Capital de Australia?", mutableListOf("Bruselas","Roma","Canberra"),"Canberra"),
            Quiz(7,"Capital de Iraq?", mutableListOf("Bagdad","Roma","San Jose"),"Bagdad"),
            Quiz(8,"Capital de Ecuador?", mutableListOf("Quito","Roma","Londres"),"Quito"),
            Quiz(9,"Capital de Peru?", mutableListOf("Madrid","Bagdad","Lima"),"Lima"),
            Quiz(10,"Capital de Marruecos?", mutableListOf("Bogota","Rabat","Londres"),"Rabat")
        )
    }
}