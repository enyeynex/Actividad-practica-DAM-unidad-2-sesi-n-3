package pe.edu.upeu.pharmamobil.domain.platform

/**
 * Contrato del dominio para compartir un texto con otras aplicaciones.
 *
 * Es una interfaz y no un expect porque cada plataforma necesita algo que
 * commonMain no conoce: un Context en Android y un controlador de vista en
 * iOS. La implementacion se inyecta con Koin desde cada platformModule.
 */
interface Compartidor {
    fun compartir(texto: String)
}
