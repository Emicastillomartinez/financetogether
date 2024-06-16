// GroupsScreen.kt
package dev.financetogether.financetogether.ui.screens.groups

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.financetogether.financetogether.ui.components.GroupItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(navController: NavController, groupViewModel: GroupViewModel) {
    val groups = groupViewModel.groups.collectAsState().value
    val currentUserEmail = groupViewModel.getCurrentUserEmail()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Grupos") })
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                groups.filter { it.members.contains(currentUserEmail) }.forEach { group ->
                    GroupItem(group, navController)
                }
            }
        }
    )
}