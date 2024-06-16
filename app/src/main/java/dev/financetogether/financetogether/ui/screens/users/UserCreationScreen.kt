@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.financetogether.financetogether.ui.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import dev.financetogether.financetogether.data.model.User
import dev.financetogether.financetogether.data.repository.UserRepository
import kotlinx.coroutines.launch

@Composable
fun userCreationScreen(userRepository: UserRepository) {
    var email by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    var emailExists by remember { mutableStateOf<Boolean?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var isChecking by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var addUserTrigger by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Edad") },
        )

        Button(onClick = {
            coroutineScope.launch {
                emailExists = userRepository.emailExists(email)
                showAlert = true // Mostrar la alerta después de comprobar el email
                isChecking = false
            }
        }, enabled = !isChecking) {
            Text("Comprobar Email y Crear Usuario")
        }
    }
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Alerta") },
            text = {
                Text(
                    if (emailExists == true) "El email ya existe. Por favor, intenta con otro." else "Email disponible. Usuario creado.",
                )
            },
            confirmButton = {
                Button(onClick = {

                    showAlert = false
                    emailExists = null // Restablecer el estado para la próxima comprobación.
                }) {
                    Text("Aceptar")
                }
            },
        )
    }
}
