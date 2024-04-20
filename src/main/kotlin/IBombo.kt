package org.example

interface IBombo {
    fun sacarBolas(cantidad: Int = 1): List<Int>

    fun obtenerBolasRestantes(): Int
}