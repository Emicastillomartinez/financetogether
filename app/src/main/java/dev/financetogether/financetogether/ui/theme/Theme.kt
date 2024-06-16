package dev.financetogether.financetogether.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define tus colores personalizados. Reemplaza con los códigos de color exactos de tu logo.
private val LightPrimary = Color(0xFF006837) // Un verde ejemplo, ajusta a tu verde
private val LightSecondary = Color(0xFF0055FF) // Un azul ejemplo, ajusta a tu azul
private val LightBackground = Color(0xFFFFFFFF) // Un fondo claro
private val LightSurface = Color(0xFFFFFFFF) // Un color de superficie claro
// ... puedes definir más colores según necesites

// Define tus colores personalizados para el tema oscuro si es necesario.
private val DarkPrimary = Color(0xFF4CAF50) // Un verde oscuro
private val DarkSecondary = Color(0xFF3F51B5) // Un azul oscuro
// ... y así sucesivamente

// Crear esquemas de color para los temas claro y oscuropackage dev.financetogether.financetogether.ui.theme
//
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//
//// Define tus colores personalizados. Reemplaza con los códigos de color exactos de tu logo.
//private val LightPrimary = Color(0xFF006837) // Un verde ejemplo, ajusta a tu verde
//private val LightSecondary = Color(0xFF0055FF) // Un azul ejemplo, ajusta a tu azul
//private val LightBackground = Color(0xFFFFFFFF) // Un fondo claro
//private val LightSurface = Color(0xFFFFFFFF) // Un color de superficie claro
//// ... puedes definir más colores según necesites
//
//// Define tus colores personalizados para el tema oscuro si es necesario.
//private val DarkPrimary = Color(0xFF4CAF50) // Un verde oscuro
//private val DarkSecondary = Color(0xFF3F51B5) // Un azul oscuro
//// ... y así sucesivamente
//
//// Crear esquemas de color para los temas claro y oscuro
//val LightColorScheme =
//    lightColorScheme(
//        primary = LightPrimary,
//        secondary = LightSecondary,
//        background = LightBackground,
//        surface = LightSurface,
//        // ... cualquier otro color que necesites configurar
//    )
//
//val DarkColorScheme =
//    darkColorScheme(
//        primary = DarkPrimary,
//        secondary = DarkSecondary,
//        // ... cualquier otro color para el tema oscuro
//    )
//
//// Luego, aplica este esquema de color en tu Composable de tema:
//@Composable
//fun FinanceTogetherTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(), // Respeta la configuración de tema oscuro del sistema
//    content: @Composable () -> Unit,
//) {
//    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
//
//    MaterialTheme(
//        colorScheme = colors,
//        typography = Typography,
//        content = content,
//    )
//}
val LightColorScheme =
    lightColorScheme(
        primary = LightPrimary,
        secondary = LightSecondary,
        background = LightBackground,
        surface = LightSurface,
        // ... cualquier otro color que necesites configurar
    )

val DarkColorScheme =
    darkColorScheme(
        primary = DarkPrimary,
        secondary = DarkSecondary,
        // ... cualquier otro color para el tema oscuro
    )

// Luego, aplica este esquema de color en tu Composable de tema:
@Composable
fun FinanceTogetherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Respeta la configuración de tema oscuro del sistema
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}
