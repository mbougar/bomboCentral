interface IGestorConsola {
    fun mostrar(message: String, saltoLinea: Boolean)

    fun obtenerInt(message: String): Int

    fun obtenerString(message: String): String
}