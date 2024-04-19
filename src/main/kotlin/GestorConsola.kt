import java.util.*

class GestorConsola: IGestorConsola {
    override fun mostrar(message: String, saltoLinea: Boolean) {
        print(message)
        if (saltoLinea) {
            println()
        }
    }

    override fun obtenerInt(message: String): Int {
        mostrar(message, false)
        var verify = false
        var num: Int? = null
        while (!verify) {
            num = readln().toIntOrNull()
            if (num == null) {
                mostrar("Número no válido, vuelve a intentarlo: ", false)
            }
            else {
                verify = true
            }
        }
        return num!!
    }

    override fun obtenerString(message: String): String {
        mostrar(message, false)
        return readln()
    }

    companion object{
        fun pausa() {
            println("\nPulsa enter para continuar...")
            Scanner(System.`in`).nextLine()
        }
    }

}