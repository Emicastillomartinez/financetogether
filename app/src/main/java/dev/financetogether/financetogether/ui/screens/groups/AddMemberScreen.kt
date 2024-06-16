// AddMemberScreen.kt
package dev.financetogether.financetogether.ui.screens.groups

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.financetogether.financetogether.components.BackButton
import dev.financetogether.financetogether.util.Validator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberScreen(navController: NavController, groupViewModel: GroupViewModel, groupId: String) {
    var memberEmail by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Miembro") },
                navigationIcon = { BackButton(navController) }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = memberEmail,
                    onValueChange = { memberEmail = it },
                    label = { Text("Correo del miembro") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (showError) {
                    Text(
                        "El correo electrónico no es válido.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (Validator.validateEmail(memberEmail)) {
                            groupViewModel.addMemberToGroup(groupId, memberEmail)
                            navController.popBackStack()
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Añadir Miembro")
                }
            }
        }
    )
}