@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.financetogether.financetogether.ui.screens.users

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dev.financetogether.financetogether.R
import dev.financetogether.financetogether.components.buttons.signInButton
import dev.financetogether.financetogether.components.buttons.signInEmail

@Composable
fun welcomeScreen(
    userViewModel: UserViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    val googleSignInClient =
        remember {
            GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
        }
    val signInIntent =
        remember {
            googleSignInClient.signInIntent
        }
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            task.addOnCompleteListener { completedTask ->
                try {
                    val account = completedTask.getResult(ApiException::class.java)
                    // Suponiendo que tienes un método en tu ViewModel que acepte el token de Google y maneje el inicio de sesión
                    userViewModel.signInWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Manejar excepción
                }
            }
        }
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier =
            Modifier
                .weight(1f) // Esta Columna toma 1 parte del espacio disponible
                .padding(top = 16.dp),
            // Sólo un pequeño padding superior para evitar que el contenido toque el borde
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_icon),
                contentDescription = "Logo",
                modifier =
                Modifier
                    .fillMaxWidth(fraction = 0.5f),
            )

            Text(
                text = "Bienvenido a\n Finance Together",
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier =
            Modifier
                .weight(1f),
            // Esta Columna también toma 1 parte del espacio disponible
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            signInButton(
                text = "Continuar con Google",
                onButtonClick = { launcher.launch(signInIntent) },
                icon = ImageVector.vectorResource(id = R.drawable.ic_google_icon),
                modifier =
                Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            signInEmail(
                text = "Continuar con Email",
                onButtonClick = { navController.navigate("SignInEmail") },
                icon = Icons.Default.Email,
                modifier =
                Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(46.dp))
            TextButton(onClick = { navController.navigate("Register") }) {
                Text(text = "Registrarse en Finance Together")
            }
        }
    }
}