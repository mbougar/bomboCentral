package org.example

class GestorConsola: IGestorConsola {
    override fun mostrarMensaje(mensaje: String) {
        print(mensaje)
    }

    override fun obtenerInt(mensaje: String, min: Int, max: Int): Int {
        var entero: Int? = null
        while (entero == null) {
            mostrarMensaje(mensaje)
            try {
                entero = readln().toInt()
                if (entero !in min..max) {
                    entero = null
                }
            } catch (e: NumberFormatException) {
                mostrarMensaje("**Error** El valor introducido no es válido, intentelo otra vez.\n")
            }
        }
        return entero
    }

    override fun obtenerString(mensaje: String): String {
        mostrarMensaje(mensaje)
        return readln()
    }
}