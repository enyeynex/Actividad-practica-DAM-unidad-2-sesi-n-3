package pe.edu.upeu.pharmamobil.domain.usecase

import pe.edu.upeu.pharmamobil.domain.model.Producto
import pe.edu.upeu.pharmamobil.platform.formatearSoles

/**
 * El texto que se comparte se arma una sola vez, en codigo comun: las dos
 * plataformas envian exactamente el mismo mensaje y solo cambia el formato
 * del precio, que resuelve formatearSoles.
 */
fun Producto.comoTextoParaCompartir(): String =
    "$nombre — ${formatearSoles(precio)} · Stock: $stock"
