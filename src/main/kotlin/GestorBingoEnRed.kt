package org.example

import IGestorConsola
import IGestorFicheros
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class GestorBingoEnRed(private val bombo: IBombo, private val gestorFicheros: IGestorFicheros, private val gestorConsola: IGestorConsola) {

    private var lineaCantada = false
    private var aTresBolasCantado = false
    private var bingoCantado = false
    private var bolas = listOf<Int>()
    private var primerBingo = ""

    fun iniciarBingo() {
        var ultimaLinea = 0
        val numJugadores = pedirJugadores()
        val fechaString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        val rutaCarpetaBingo = "\\\\PCProfe\\Bingo"
        val rutaFicheroBombo = "$rutaCarpetaBingo\\logoBingoCentral_$fechaString.txt"
        val rutaCarpetaJugadores = rutaCarpetaBingo
        val rutaFicheroJugadores = "$rutaCarpetaJugadores\\logoBingoCentralUsuarios_$fechaString.txt"

        val ficheroBingo = gestorFicheros.crearFichero(rutaFicheroBombo, "")
        val ficheroJugadores = gestorFicheros.crearFichero(rutaFicheroJugadores, "")

        if (ficheroBingo != null && ficheroJugadores != null) {

            gestorConsola.mostrar("Buscando info de jugadores...", false)

            var jugadoresListos = 0
            var contadorIteraciones = 0
            while (jugadoresListos != numJugadores && contadorIteraciones < 120) {

                val contenidoFicheroJugadores = gestorFicheros.leerFichero(ficheroJugadores)
                if (contenidoFicheroJugadores != null) {
                    jugadoresListos = contenidoFicheroJugadores.count { it.matches(Regex("\\b\\w+\\s*-\\s*ok\\b")) }
                }
                Thread.sleep(1000)
                contadorIteraciones++
            }

            while (!bingoCantado) {
                var contenidoFicheroJugadores = gestorFicheros.leerFichero(ficheroJugadores)

                sacarBolas(ficheroBingo)

                while (contenidoFicheroJugadores == null || contenidoFicheroJugadores.size < ultimaLinea + numJugadores) {
                        contenidoFicheroJugadores = gestorFicheros.leerFichero(ficheroJugadores)
                }

                for (linea in ultimaLinea..ultimaLinea + 3) {
                    comprobarEstadoPartida(contenidoFicheroJugadores[linea])
                }

                ultimaLinea += 3
            }

            gestorFicheros.escribirFichero(ficheroBingo, "\n¡Bingo de $primerBingo!")
        }
    }

    private fun sacarBolas(fichero: File) {
        bolas = bombo.sacarBolas(obtenerBolasASacar())
        gestorFicheros.escribirFichero(fichero, bolas.joinToString(" ") + "\n")
    }

    private fun comprobarEstadoPartida(linea: String) {
        comprobarLinea(linea)
        comprobarATresBolas(linea)
        comprobarBingo(linea)
    }

    private fun comprobarLinea(linea: String) {
        if (!lineaCantada) {
            val elementosLinea = linea.split("-").map { it.trim() }
            if (elementosLinea.size > 4) {
                if (elementosLinea[5] == "Linea" || elementosLinea[5] == "Linea\n") {
                    lineaCantada = true
                }
            }
        }
    }

    private fun comprobarATresBolas(linea: String) {
        if (!aTresBolasCantado) {
            val elementosLinea = linea.split("-").map { it.trim() }
            if (elementosLinea.size > 4) {
                if (elementosLinea[5] == "Solo1" || elementosLinea[5] == "Solo1\n") {
                    aTresBolasCantado = true
                }
            }
        }
    }

    private fun comprobarBingo(linea: String) {
        if (!bingoCantado) {
            val elementosLinea = linea.split("-").map { it.trim() }
            if (elementosLinea.size > 4) {
                if (elementosLinea[5] == "Bingo" || elementosLinea[5] == "Bingo\n") {
                    bingoCantado = true
                    primerBingo = elementosLinea[0]
                }
            }
        }
    }

    private fun pedirJugadores(): Int {
        var numJugadores = 0

        while (numJugadores !in 1..10) {
            numJugadores = gestorConsola.obtenerInt("Introduzca el número de jugadores: ")
        }


        return numJugadores
    }

    private fun obtenerBolasASacar(): Int {
        return when {
            aTresBolasCantado -> 1
            lineaCantada -> 3
            else -> Random.nextInt(4, 7)
        }
    }

}