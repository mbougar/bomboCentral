package org.example

interface IGestorFicheros {
    fun crearCarpeta(ruta: String)
    fun crearFichero(ruta: String)
    fun leerFIchero(ruta:String): List<String>
    fun escribirFichero(ruta:String, mensaje:String)
}