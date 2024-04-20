package org.example

import IGestorConsola
import IGestorFicheros
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class GestorBingoEnRed(private val bombo: IBombo, private val gestorFicheros: IGestorFicheros, private val gestorConsola: IGestorConsola, private val rutaCarpetaBingo: String) {

    private var lineaCantada = false
    private var aSolo1Cantado = false
    private var bingoCantado = false
    private var bolas = listOf<Int>()
    private var primerBingo = ""

    fun iniciarBingo() {
        var ultimaLinea = 0
        val numJugadores = pedirJugadores()
        val fechaString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        val rutaFicheroBombo = "$rutaCarpetaBingo\\bomboBingoCentral_$fechaString.txt"
        val rutaFicheroJugadores = "$rutaCarpetaBingo\\bomboBingoCentralUsuarios_$fechaString.txt"

        val ficheroBingo = gestorFicheros.crearFichero(rutaFicheroBombo, "")
        val ficheroJugadores = gestorFicheros.crearFichero(rutaFicheroJugadores, "")

        if (ficheroBingo != null && ficheroJugadores != null) {

            gestorConsola.mostrar("Buscando info de jugadores...", false)

            var jugadoresListos = 0
            var contadorIteraciones = 0
            while (jugadoresListos != numJugadores && contadorIteraciones < 120) {

                val contenidoFicheroJugadores: List<String>? = try {
                    gestorFicheros.leerFichero(ficheroJugadores)
                } catch (e: Exception) {
                    null
                }

                if (contenidoFicheroJugadores != null) {
                    jugadoresListos = contenidoFicheroJugadores.count { it.matches(Regex("\\b\\w+\\s*-\\s*ok\\b")) }
                }

                gestorConsola.mostrar(".", false)

                if (jugadoresListos != numJugadores) {
                    Thread.sleep(500)
                    contadorIteraciones++
                }
            }

            gestorConsola.mostrar("\nTodos los jugadores encontrados", true)

            ultimaLinea += numJugadores

            while (!bingoCantado) {

                var contenidoFicheroJugadores: List<String>? = null

                sacarBolas(ficheroBingo)

                while (contenidoFicheroJugadores == null || contenidoFicheroJugadores.size < ultimaLinea + numJugadores) {
                    contenidoFicheroJugadores = try {
                        gestorFicheros.leerFichero(ficheroJugadores)
                    } catch (e: Exception) {
                        null
                    }
                }

                repeat(numJugadores) {
                    comprobarEstadoPartida(contenidoFicheroJugadores[ultimaLinea + it])
                }

                ultimaLinea += numJugadores
            }

            var haEscrito = false
            while (!haEscrito) {
                try {
                    haEscrito = gestorFicheros.escribirFichero(ficheroBingo, "\n¡Bingo de $primerBingo!")
                } catch (e: Exception) {
                    haEscrito = false
                    Thread.sleep(500)
                }
            }
            gestorConsola.mostrar("\n¡Bingo de $primerBingo!", false)
        }
    }

    private fun sacarBolas(fichero: File) {
        bolas = bombo.sacarBolas(obtenerBolasASacar())
        var haEscrito = false
        while (!haEscrito) {
            try {
                haEscrito = gestorFicheros.escribirFichero(fichero, bolas.joinToString(" ") + "\n")
            } catch (e: Exception) {
                haEscrito = false
                Thread.sleep(500)
            }
        }
    }

    private fun comprobarEstadoPartida(linea: String) {
        comprobarLinea(linea)
        comprobarASolo1(linea)
        comprobarBingo(linea)
    }

    private fun comprobarLinea(linea: String) {
        if (!lineaCantada) {
            val elementosLinea = linea.split("-").map { it.trim() }
            if (elementosLinea.size > 4) {
                if (elementosLinea[4] == "Linea" || elementosLinea[4] == "Linea\n") {
                    lineaCantada = true
                }
            }
        }
    }

    private fun comprobarASolo1(linea: String) {
        if (!aSolo1Cantado) {
            val elementosLinea = linea.split("-").map { it.trim() }
            if (elementosLinea.size > 4) {
                if (elementosLinea[4] == "Solo1" || elementosLinea[4] == "Solo1\n") {
                    aSolo1Cantado = true
                }
            }
        }
    }

    private fun comprobarBingo(linea: String) {
        if (!bingoCantado) {
            val elementosLinea = linea.split("-").map { it.trim() }
            if (elementosLinea.size > 4) {
                if (elementosLinea[4] == "Bingo" || elementosLinea[4] == "Bingo\n") {
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
        val bolasASacar = when {
            aSolo1Cantado -> 1
            lineaCantada -> 3
            else -> Random.nextInt(4, 7)
        }

        return if (bolasASacar > bombo.obtenerBolasRestantes()) {
            bombo.obtenerBolasRestantes()
        } else {
            bolasASacar
        }
    }
}