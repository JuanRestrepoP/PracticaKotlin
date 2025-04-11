package com.example.myapplication.tarea

open class Lavadora(val marca: String, val año: Int, val capacidad: Int) : Electrodomestico("Lavadora") {

    fun info(): String = "El electrodomestico: ${name ?: ""}, de marca: $marca, del año: $año con capacidad: $capacidad kg."

    fun ciclos(): Int {
        return when {
            capacidad <= 0 -> 0
            capacidad in 1..5 -> 3
            capacidad in 6..10 -> 5
            capacidad in 10..15 -> 7
            capacidad in 15..20->10
            capacidad >20 -> 15
            else -> 0
        }
    }

    fun tiempoCiclos() {
        val ciclos = ciclos()
        var tiempo = 0
        for (i in 0 until ciclos) {
            tiempo += 4
        }
        println("La lavadora ${marca} con capacidad de $capacidad kg necesita $ciclos ciclos de lavado")
        println("Tiempo total estimado: $tiempo minutos.")
    }

}
