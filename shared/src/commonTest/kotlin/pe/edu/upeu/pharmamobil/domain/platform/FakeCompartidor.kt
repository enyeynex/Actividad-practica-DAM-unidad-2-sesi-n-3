package pe.edu.upeu.pharmamobil.domain.platform

/**
 * Doble del Compartidor para las pruebas: en vez de abrir el selector del
 * sistema guarda lo que se le pidio compartir. Es la ventaja de haberlo
 * declarado como interfaz inyectada y no como expect.
 */
class FakeCompartidor : Compartidor {

    val textosCompartidos = mutableListOf<String>()

    override fun compartir(texto: String) {
        textosCompartidos.add(texto)
    }
}
