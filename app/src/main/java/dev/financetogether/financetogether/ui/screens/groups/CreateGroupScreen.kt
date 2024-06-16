package dev.financetogether.financetogether.ui.screens.groups

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.financetogether.financetogether.components.BackButton
import dev.financetogether.financetogether.data.model.Group
import dev.financetogether.financetogether.ui.screens.users.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(navController: NavController, groupViewModel: GroupViewModel, userViewModel: UserViewModel) {
    var groupName by remember { mutableStateOf("") }
    var memberEmail by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val currentUser by userViewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Grupo") },
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
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text("Nombre del grupo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción del grupo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = memberEmail,
                    onValueChange = { memberEmail = it },
                    label = { Text("Añadir miembro (Email)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val newGroup = Group(
                            name = groupName,
                            adminEmail = currentUser?.email ?: "",
                            description = description,
                            members = listOfNotNull(currentUser?.email, memberEmail.takeIf { it.isNotBlank() })
                        )
                        groupViewModel.createGroup(newGroup)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Crear grupo")
                }
            }
        }
    )
}