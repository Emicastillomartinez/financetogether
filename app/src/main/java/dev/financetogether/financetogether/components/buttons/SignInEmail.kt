@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.financetogether.financetogether.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun signInEmail(
    text: String,
    onButtonClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onButtonClick,
        modifier =
            modifier
                .height(50.dp)
                .fillMaxWidth().border(1.dp, Color.Black, RoundedCornerShape(50.dp)),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary, // Fondo blanco
                contentColor = Color.Black, // Texto e ícono negro
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Email Icon",
            )
            Spacer(Modifier.width(16.dp)) // Espacio entre ícono y texto
            Text(
                text = text,
                modifier =
                    Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally), // Asegúrate de que el texto se centre en el espacio restante
            )
        }
    }
}
