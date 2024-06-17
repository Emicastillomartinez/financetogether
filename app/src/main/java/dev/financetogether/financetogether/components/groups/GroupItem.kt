// GroupItem.kt
package dev.financetogether.financetogether.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.financetogether.financetogether.data.model.Group
import dev.financetogether.financetogether.ui.screens.groups.GroupViewModel
import dev.financetogether.financetogether.util.Utils.formatCurrency

@Composable
fun GroupItem(group: Group, navController: NavController, groupViewModel: GroupViewModel) {
    val totalContributions by groupViewModel.totalContributions.collectAsState()

    LaunchedEffect(group.id) {
        groupViewModel.getTotalContributions(group.id)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("GroupDetails/${group.id}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = group.name, style = MaterialTheme.typography.titleMedium, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = group.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(text = "Miembros: ${group.members.size}", style = MaterialTheme.typography.bodySmall, color = Color.White)

        }
    }
}
