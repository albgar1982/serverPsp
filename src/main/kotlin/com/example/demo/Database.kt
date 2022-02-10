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
            println("Base de datos creada.")
        }
    }

}