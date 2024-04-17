package org.example

class Bombo: IBombo {
    private val pila: Pila<Int>

    init {
        val bolas: MutableList<Int> = (1..90).toMutableList()
        bolas.shuffle()
        pila = Pila(bolas)
    }

    override fun sacarBolas(cantidad: Int): List<Int> {

        when {
            cantidad > pila.size() -> throw Exception("No hay suficientes bolas en el bombo para sacar")
            else -> {
                val bolasSacadas = mutableListOf<Int>()
                repeat(cantidad) {
                    val bola = pila.pop()
                    bolasSacadas.add(bola)
                }

                return bolasSacadas
            }
        }
    }
}