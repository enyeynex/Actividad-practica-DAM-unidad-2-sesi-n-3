package pe.edu.upeu.pharmamobil.platform

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Los separadores y el espacio tras el simbolo cambian segun la plataforma,
 * asi que la prueba comun solo comprueba lo que ambas deben cumplir.
 */
class FormatoTest {

    @Test
    fun formateaConElSimboloDelSolYDosDecimales() {

        val texto = formatearSoles(12.5)

        assertTrue(texto.startsWith("S/"), "Se esperaba el simbolo S/ en \"$texto\"")
        assertEquals("1250", texto.filter { it.isDigit() })
    }
}
