package org.example

interface IGestorConsola {
    fun mostrarMensaje(mensaje: String)

    fun obtenerInt(mensaje: String, min: Int, max: Int): Int

    fun obtenerString(mensaje: String): String
}