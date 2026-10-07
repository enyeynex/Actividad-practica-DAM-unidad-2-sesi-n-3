package pe.edu.upeu.pharmamobil.presentation.detalle

import pe.edu.upeu.pharmamobil.presentation.producto.ProductoUi

/** Estados excluyentes del detalle: solo uno puede estar activo. */
sealed interface DetalleProductoUiState {

    data object Cargando : DetalleProductoUiState

    data class Listo(val producto: ProductoUi) : DetalleProductoUiState

    data class Error(val mensaje: String) : DetalleProductoUiState
}
