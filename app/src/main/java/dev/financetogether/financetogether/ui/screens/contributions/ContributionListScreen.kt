package dev.financetogether.financetogether.ui.screens.contributions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ContributionListScreen(navController: NavController, contributionViewModel: ContributionViewModel, groupId: String) {
    val contributions by contributionViewModel.contributions.collectAsState()

    LaunchedEffect(Unit) {
        contributionViewModel.loadContributions(groupId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Contribuciones",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        contributions.forEach { contribution ->
            Text(
                text = "${contribution.userEmail}: ${contribution.amount} €",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("AddContribution/$groupId") }) {
            Text("Añadir Contribución")
        }
    }
}
