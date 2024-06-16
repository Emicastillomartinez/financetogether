// MemberItem.kt
package dev.financetogether.financetogether.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.financetogether.financetogether.ui.screens.groups.GroupViewModel

@Composable
fun MemberItem(email: String, groupId: String, groupViewModel: GroupViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val user by groupViewModel.userByEmail.collectAsState()
    LaunchedEffect(email) {
        groupViewModel.getUserByEmail(email)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user?.name?.firstOrNull()?.toString() ?: email.first().toString(),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = user?.name ?: email, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            }
            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar miembro", tint = Color.Red)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar a este miembro del grupo?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        groupViewModel.removeMemberFromGroup(groupId, email)
                        showDialog = false
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}