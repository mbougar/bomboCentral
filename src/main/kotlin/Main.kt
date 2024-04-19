package org.example

import Bombo
import GestorConsola
import GestorFicheros

fun main() {

    val gestorConsola = GestorConsola()
    val gestorFicheros = GestorFicheros(gestorConsola)
    val bombo = Bombo()

    val gestorBingo = GestorBingoEnRed(bombo, gestorFicheros, gestorConsola)

    gestorBingo.iniciarBingo()
}