package com.example.moviestime.core

import java.lang.Exception

sealed class Resource<out T> {

    //Retorna valor cargando
    class Loading<out T>: Resource<T>()

    //Entrega data exitosa, devuelve un Resource de success
    data class Success<out T>(val data: T): Resource<T>()

    data class Failure(val exception: Exception): Resource<Nothing>()

}