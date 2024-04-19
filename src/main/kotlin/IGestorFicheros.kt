import java.io.File

interface IGestorFicheros {
    fun comprobarDirectorio(ruta: String):Boolean

    fun comprobarFichero(ruta: String):Boolean

    fun escribirFichero(fichero:File, mensaje:String):Boolean

    fun leerFichero(fichero: File): List<String>?

    fun crearDirectorio(ruta: String):Boolean

    fun crearFichero(ruta: String, mensaje: String, sobreescribir:Boolean = true):File?
}