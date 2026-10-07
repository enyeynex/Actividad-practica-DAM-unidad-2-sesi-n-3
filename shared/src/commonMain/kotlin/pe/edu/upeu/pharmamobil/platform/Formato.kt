package pe.edu.upeu.pharmamobil.platform

/**
 * PharmaSoft envia el precio como numero; mostrarlo con el formato de moneda
 * del Peru es responsabilidad de cada plataforma. Aqui solo se declara que la
 * funcion debe existir: el compilador exige un actual en androidMain y otro
 * en iosMain.
 */
expect fun formatearSoles(valor: Double): String
