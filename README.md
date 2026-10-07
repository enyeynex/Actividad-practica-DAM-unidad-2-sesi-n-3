This is a Kotlin Multiplatform project targeting Android, iOS.

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

## Capacidades nativas

Sesión 9 · mecanismo `expect`/`actual` e implementaciones por plataforma. La
interfaz y el dominio siguen en `commonMain`; solo lo que depende del sistema
operativo vive en `androidMain` e `iosMain`.

| Capacidad | Mecanismo | Por qué |
|---|---|---|
| Formato de moneda (`formatearSoles`) | `expect` / `actual` | Función pura, sin dependencias: el compilador exige las dos implementaciones. |
| Compartir un producto (`Compartidor`) | Interfaz en `domain` + inyección con Koin | Necesita un `Context` en Android y un controlador de vista en iOS; en las pruebas se sustituye por un doble. |

### Archivos por plataforma

Rutas relativas a `shared/src/<sourceSet>/kotlin/pe/edu/upeu/pharmamobil/`.

| | `commonMain` | `androidMain` | `iosMain` |
|---|---|---|---|
| Formato de moneda | `platform/Formato.kt` (`expect`) | `platform/Formato.android.kt` · `java.text.NumberFormat` con `Locale("es", "PE")` | `platform/Formato.ios.kt` · `NSNumberFormatter` con `NSLocale("es_PE")` |
| Compartir | `domain/platform/Compartidor.kt` (interfaz) | `platform/CompartidorAndroid.kt` · `Intent.ACTION_SEND` + selector del sistema | `platform/CompartidorIos.kt` · `UIActivityViewController` |
| Registro en Koin | `di/AppModule.kt` (`expect val platformModule`) | `di/PlatformModule.android.kt` | `di/PlatformModule.ios.kt` |

### Dónde se usa, en código común

- `presentation/producto/ProductoUi.kt`: el mapeo `Producto.aUi()` aplica `formatearSoles`; ni el dominio ni los composables formatean precios.
- `domain/usecase/TextoParaCompartir.kt`: `Producto.comoTextoParaCompartir()` arma el texto que envían las dos plataformas.
- `presentation/detalle/`: `DetalleProductoViewModel` recibe el `Compartidor` por constructor y `DetalleProductoScreen` muestra el botón «Compartir». Al detalle se llega tocando un producto del inventario.
- Ninguna clase de `presentation` importa paquetes `android.` ni `platform.UIKit`.

### Diferencias entre plataformas

- **Precio:** cada formateador decide el espacio tras el símbolo y los separadores, por eso el texto exacto puede variar entre Android e iOS.
- **Compartir:** Android abre el selector del sistema y necesita `FLAG_ACTIVITY_NEW_TASK`, porque Koin entrega el `Context` de la aplicación y no el de una Activity. iOS presenta la hoja de compartir desde el controlador raíz de la ventana.

### Verificación

```
./gradlew :shared:testAndroidHostTest
./gradlew :androidApp:assembleDebug
./gradlew :shared:compileKotlinIosSimulatorArm64
```

El último comando compila `iosMain` también desde Windows o Linux; enlazar el framework y ejecutar en el simulador requiere macOS con Xcode.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…