package org.example
import java.io.File

class GestorFicheros: IGestorFicheros {

    override fun crearCarpeta(ruta: String) {
        val carpeta = File(ruta)

        if (!carpeta.exists()) {
            carpeta.mkdir()
        }
    }

    override fun crearFichero(ruta: String) {
        val file = File(ruta)
        file.createNewFile()
    }

    override fun leerFIchero(ruta:String): List<String> {
        val fichero = File(ruta)
        val lineas = fichero.readLines()
        return lineas
    }

    override fun escribirFichero(ruta:String, mensaje:String) {
        val fichero = File(ruta)
        fichero.writeText(mensaje)
    }
}