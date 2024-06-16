@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.financetogether.financetogether.ui.screens

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import dev.financetogether.financetogether.R
import dev.financetogether.financetogether.ui.screens.users.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun splashScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
) {
    var isSplashScreenShown by remember { mutableStateOf(true) }

    val isLoggedState = userViewModel.isAuthenticated.collectAsState(initial = null)

    LaunchedEffect(isLoggedState.value) {
        delay(3000)
        isSplashScreenShown = false

        isLoggedState.value?.let { isAuthenticated ->
            Log.d("isAuthenticated", "Current user is: $isAuthenticated")
            if (isAuthenticated) {
                // Si el usuario está autenticado, navegar a la pantalla de bienvenida (Home).
                navController.navigate("Home") {
                    popUpTo("Splash") { inclusive = true }
                }
            } else {
                // Si el usuario no está autenticado, navegar a la pantalla de registro/inicio de sesión (Welcome).
                navController.navigate("Welcome") {
                    popUpTo("Splash") { inclusive = true }
                }
            }
        }
    }

    if (isSplashScreenShown) {
        val infiniteTransition = rememberInfiniteTransition(label = "")
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(750, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "",
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier =
                    Modifier
                        .fillMaxWidth(fraction = 0.5f) // Ajusta esto según lo grande que quieras la imagen
                        .aspectRatio(1f) // Mantiene la proporción de aspecto
                        .scale(scale), // Aplicar el valor de escala animado para efecto de pulso
            )
        }
    }
}
