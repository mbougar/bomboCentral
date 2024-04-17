package org.example

class Pila<T>(private val elementos: MutableList<T>) {

    fun push(elemento: T) {
        elementos.add(elemento)
    }

    fun pop(): T {
        if (isEmpty()) {
            throw NoSuchElementException("La pila está vacía")
        }
        return elementos.removeAt(elementos.size - 1)
    }

    fun top(): T {
        if (isEmpty()) {
            throw NoSuchElementException("La pila está vacía")
        }
        return elementos[elementos.size - 1]
    }

    private fun isEmpty(): Boolean {
        return elementos.isEmpty()
    }

    fun size(): Int {
        return elementos.size
    }
}