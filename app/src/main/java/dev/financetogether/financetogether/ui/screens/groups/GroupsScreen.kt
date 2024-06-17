// GroupsScreen.kt
package dev.financetogether.financetogether.ui.screens.groups

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    val currentUserEmail = groupViewModel.currentUser.collectAsState().value?.email

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Grupos") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                groups.forEach { group ->
                    GroupItem(group, navController, groupViewModel)
                }
            }
        }
    )
}
