import org.example.GestorBingoEnRed

fun main() {

    val gestorConsola = GestorConsola()
    val gestorFicheros = GestorFicheros(gestorConsola)
    val bombo = Bombo()
    val rutaCarpetaBingo = "\\\\PCProfe\\Bingo"

    val gestorBingo = GestorBingoEnRed(bombo, gestorFicheros, gestorConsola, rutaCarpetaBingo)

    gestorBingo.iniciarBingo()
}