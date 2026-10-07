package pe.edu.upeu.pharmamobil.presentation.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pe.edu.upeu.pharmamobil.presentation.components.EstadoVacio
import pe.edu.upeu.pharmamobil.presentation.producto.ProductoUi

@Composable
fun DetalleProductoScreen(
    productoId: Long,
    viewModel: DetalleProductoViewModel,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(productoId) {
        viewModel.cargar(productoId)
        onDispose {
            viewModel.limpiar()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when (val estado = uiState) {

            DetalleProductoUiState.Cargando ->
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

            is DetalleProductoUiState.Listo ->
                DetalleProducto(
                    producto = estado.producto,
                    onCompartir = viewModel::compartirProductoActual
                )

            is DetalleProductoUiState.Error ->
                EstadoVacio(
                    icono = Icons.Default.CloudOff,
                    titulo = "No pudimos mostrar el producto",
                    descripcion = estado.mensaje,
                    colorIcono = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center),
                    accion = {
                        FilledTonalButton(onClick = onVolver) {
                            Text("Volver al inventario")
                        }
                    }
                )
        }
    }
}


@Composable
private fun DetalleProducto(
    producto: ProductoUi,
    onCompartir: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {

                        Icon(
                            imageVector = Icons.Default.Medication,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(28.dp)
                        )
                    }

                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider()

                Dato(etiqueta = "Precio", valor = producto.precio)

                Dato(etiqueta = "Stock", valor = producto.stock)

                if (producto.requiereReposicion) {

                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ) {

                        Text(
                            text = "Stock bajo: requiere reposición",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                        )
                    }
                }
            }
        }

        // El composable no conoce la implementacion: solo dispara la accion
        // que expone el ViewModel.
        Button(
            onClick = { onCompartir() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Compartir")
        }
    }
}


@Composable
private fun Dato(
    etiqueta: String,
    valor: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = valor,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
