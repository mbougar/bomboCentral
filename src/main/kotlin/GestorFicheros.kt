import java.io.File

class GestorFicheros(private val consola: IGestorConsola):IGestorFicheros {

    override fun comprobarDirectorio(ruta: String): Boolean {
        try {
            if (File(ruta).isDirectory)
                return true
        } catch (e:SecurityException){
            consola.mostrar("Error al comprobar $ruta: ${e.message}", true)
        }
        return false
    }

    override fun comprobarFichero(ruta: String): Boolean {
        try {
            if (File(ruta).isFile)
                return true
        } catch (e:SecurityException){
            consola.mostrar("Error al comprobar $ruta: ${e.message}", true)
        }
        return false
    }

    override fun escribirFichero(fichero: File, mensaje: String): Boolean {
        try {
            fichero.appendText(mensaje)
        } catch (e:Exception){
            consola.mostrar("Error al escribir $fichero: ${e.message}", true)
            return false
        }
        return true
    }

    override fun leerFichero(fichero: File): List<String>? {
        val lista : List<String>
        try {
            lista = fichero.readLines()
        }catch (e:Exception){
            consola.mostrar("Error al leer $fichero: ${e.message}", true)
            return null
        }
        return lista
    }

    override fun crearDirectorio(ruta: String): Boolean {
        val directorio = File(ruta)
        try {
            if (!directorio.isDirectory) {
                if (!directorio.mkdirs()) {
                    return false
                }
            }
        } catch (e: Exception) {
            consola.mostrar("Error al crear el directorio $ruta: ${e.message}", true)
            return false
        }
        return true
    }

    override fun crearFichero(ruta: String, mensaje: String, sobreescribir: Boolean): File? {
        val fichero = File(ruta)
        crearDirectorio(fichero.parent)
        try {
            if (sobreescribir){
                fichero.writeText(mensaje)
            }else{
                fichero.createNewFile()
                if (mensaje.isNotEmpty()){
                    fichero.appendText(mensaje)
                }
            }
        }catch (e:Exception){
            consola.mostrar("Error al crear el fichero $ruta: ${e.message}",true)
            return null
        }
        return fichero
    }
}