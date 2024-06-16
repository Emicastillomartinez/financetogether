// MenuLateral.kt
package dev.financetogether.financetogether.components.nav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.financetogether.financetogether.ui.components.GroupItemCompact
import dev.financetogether.financetogether.ui.screens.groups.GroupViewModel

@Composable
fun MenuLateral(navController: NavController, groupViewModel: GroupViewModel, currentUserEmail: String) {
    val groups = groupViewModel.groups.collectAsState().value
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Menú Lateral",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = { navController.navigate("CreateGroup") }) {
                Text("Crear Grupo")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mis Grupos",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            groups.forEach { group ->
                GroupItemCompact(group = group, navController = navController)
            }
        }
    }
}
