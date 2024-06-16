package dev.financetogether.financetogether.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.financetogether.financetogether.R
import dev.financetogether.financetogether.ui.screens.users.UserViewModel

@Composable
fun profileScreen(navController: NavController, userViewModel: UserViewModel) {
    val user by userViewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ajustes",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // User Information
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Gray, shape = CircleShape)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user?.name?.firstOrNull()?.toUpperCase()?.toString() ?: "U",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user?.name ?: "Nombre de usuario",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = user?.email ?: "Correo electrónico",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Account Settings Section
        // Edit Profile
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("EditProfile") }
                .background(Color(0xFFA5D6A7), shape = RoundedCornerShape(8.dp))
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit Profile",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Editar perfil", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Log Out
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFA5D6A7), shape = RoundedCornerShape(8.dp))
                .clickable {
                    userViewModel.signOut()
                    navController.navigate("Welcome") {
                        popUpTo("Home") { inclusive = true }
                    }
                }
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "Log Out",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Cerrar sesión", style = MaterialTheme.typography.bodyLarge, color = Color.White)
        }
    }
}