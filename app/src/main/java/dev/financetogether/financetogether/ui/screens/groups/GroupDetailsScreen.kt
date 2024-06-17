package dev.financetogether.financetogether.ui.screens.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.financetogether.financetogether.ui.components.MemberItem
import dev.financetogether.financetogether.util.Utils.formatCurrency
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsScreen(
    navController: NavController,
    groupViewModel: GroupViewModel,
    groupId: String,
    currentUserEmail: String
) {
    val group = groupViewModel.groups.collectAsState().value.find { it.id == groupId }
    var newMemberEmail by remember { mutableStateOf("") }
    var showAddContributionDialog by remember { mutableStateOf(false) }
    var contributionAmount by remember { mutableStateOf("") }
    val emailError by groupViewModel.emailError.collectAsState()
    val contributions by groupViewModel.contributions.collectAsState()
    val totalContributions by groupViewModel.totalContributions.collectAsState()
    var contributionError by remember { mutableStateOf<String?>(null) }
    var showDeleteGroupDialog by remember { mutableStateOf(false) }

    LaunchedEffect(groupId) {
        groupViewModel.loadContributions(groupId)
        groupViewModel.getTotalContributions(groupId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Grupo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        content = { padding ->
            group?.let {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = it.description,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row {
                                IconButton(onClick = {
                                    navController.navigate("EditGroup/$groupId")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar grupo",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(onClick = { showAddContributionDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Añadir contribución",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(onClick = { showDeleteGroupDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Borrar grupo",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Miembros",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    it.members.forEach { email ->
                        MemberItem(email = email, groupId = groupId, groupViewModel = groupViewModel)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if (it.members.contains(currentUserEmail)) {
                        OutlinedTextField(
                            value = newMemberEmail,
                            onValueChange = { newMemberEmail = it },
                            label = { Text("Añadir miembro por correo") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface),
                            isError = emailError != null
                        )
                        if (emailError != null) {
                            Text(
                                text = emailError!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                groupViewModel.addMemberToGroup(groupId, newMemberEmail)
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Añadir Miembro")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Total Contribuciones: ${formatCurrency(totalContributions)}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Contribuciones",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    contributions.forEach { contribution ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Usuario: ${contribution.userName}",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "Cantidad: ${formatCurrency(contribution.amount)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    )

    if (showAddContributionDialog) {
        AlertDialog(
            onDismissRequest = { showAddContributionDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = contributionAmount.toDoubleOrNull()
                        if (amount != null) {
                            groupViewModel.addContribution(groupId, amount)
                            showAddContributionDialog = false
                            contributionError = null
                        } else {
                            contributionError = "Por favor, introduzca una cantidad válida."
                        }
                    }
                ) {
                    Text("Añadir")
                }
            },
            dismissButton = {
                Button(onClick = { showAddContributionDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Añadir Contribución") },
            text = {
                Column {
                    TextField(
                        value = contributionAmount,
                        onValueChange = { contributionAmount = it },
                        label = { Text("Cuantia de la contribución") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = contributionError != null
                    )
                    if (contributionError != null) {
                        Text(
                            text = contributionError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        )
    }

    if (showDeleteGroupDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteGroupDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        groupViewModel.deleteGroup(groupId)
                        navController.popBackStack()
                        showDeleteGroupDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteGroupDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Está seguro de que desea eliminar este grupo y todas sus contribuciones?") }
        )
    }
}

