package com.example.myapplication.tarea

fun Lavadora.capacidadMaxima(): String {
    return when {
        capacidad <= 0 -> "La capacidad ingresada no es valida."
        capacidad in 1..5 -> "El electrodomestico $name es de capacidad baja (hasta 5kg)."
        capacidad in 6..10 -> "El electrodomestico $name tiene una capacidad media (6 a 10kg)."
        capacidad > 20 -> "El electrodomestico $name aguanta mas de 20kg."
        else -> "No se pudo determinar la capacidad del electrodoméstico $name."
    }
}

fun main(){
    var lavadoraKalley = Lavadora("Kalley", 2025, 22)
    println(lavadoraKalley.info())
    println(lavadoraKalley.capacidadMaxima())
    lavadoraKalley.tiempoCiclos()
}