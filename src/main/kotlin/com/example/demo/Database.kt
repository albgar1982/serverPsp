package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class Database {

    @Bean
    fun initDatabase(userRepository: UserRepository): CommandLineRunner{
        return CommandLineRunner{
            println("Base de datos creándose")
            //val listaUser = mutableListOf(User("Alberto","noTengo1","loquesea", Calendar.getInstance(), mutableListOf(20,8)))
            println("Base de datos creada y llena de datos.")
            userRepository.findAll().forEach {
                println(it)
            }
            /*
            var p=Pokemon("Ratata",1)
            pokemonRepository.save(p)

            p.nivel = 2
            pokemonRepository.save(p)

            p.id = 10 //Si no existe el id, pondrá un id nuevo(el siguiente) y creará otro pokemon nuevo
            pokemonRepository.save(p)

            p.id = 1 //Si existe el id, cambiará el pokemon existente con ese id
            pokemonRepository.save(p)
             */
        }
    }

}