package org.example

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GestorBingoEnRed(val bombo: IBombo, val gestorFicheros: IGestorFicheros, val gestorConsola: IGestorConsola) {

    fun iniciarBingo() {
        val numJugadores = pedirJugadores()
        val fechaString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        val rutaCarpetaBingo = "C:\\Bingo"
        val rutaFicheroBombo = "$rutaCarpetaBingo\\bomboBingo_$fechaString.txt"
        val rutaCarpetaJugadores = "$rutaCarpetaBingo\\$fechaString"
        val rutaFicheroJugadores = "$rutaCarpetaJugadores\\usuarios.txt"

        gestorFicheros.crearCarpeta(rutaCarpetaBingo)
        gestorFicheros.crearFichero(rutaFicheroBombo)

        gestorConsola.mostrarMensaje("Buscando info de jugadores...")

        if (!File(rutaCarpetaJugadores).exists()) {
            File(rutaCarpetaJugadores).mkdirs()
        }

        var jugadoresListos = 0
        while (jugadoresListos != numJugadores) {
            val jugadores = listOf("jugadorGrupo1", "jugadorGrupo2", "jugadorGrupo3") // Lista de jugadores

            val contenidoFicheroJugadores = gestorFicheros.leerFIchero(rutaFicheroJugadores)
            /*
            * Si
            */
            jugadoresListos = contenidoFicheroJugadores.count { it.matches(Regex("\\b\\w+\\s*-\\s*ok\\b")) }
        }

    }

    private fun pedirJugadores(): Int {
        val numJugadores = gestorConsola.obtenerInt("Introduzca el número de jugadores: ", 1, Int.MAX_VALUE)

        return numJugadores
    }

}