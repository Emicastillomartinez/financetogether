@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.financetogether.financetogether.components.nav

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import dev.financetogether.financetogether.ui.screens.*
import dev.financetogether.financetogether.ui.screens.contributions.AddContributionScreen
import dev.financetogether.financetogether.ui.screens.contributions.ContributionViewModel
import dev.financetogether.financetogether.ui.screens.groups.AddMemberScreen
import dev.financetogether.financetogether.ui.screens.groups.CreateGroupScreen
import dev.financetogether.financetogether.ui.screens.groups.EditGroupScreen
import dev.financetogether.financetogether.ui.screens.groups.GroupDetailsScreen
import dev.financetogether.financetogether.ui.screens.groups.GroupsScreen
import dev.financetogether.financetogether.ui.screens.home.homeScreen
import dev.financetogether.financetogether.ui.screens.profile.editProfileScreen
import dev.financetogether.financetogether.ui.screens.profile.profileScreen
import dev.financetogether.financetogether.ui.screens.users.emailSignInScreen
import dev.financetogether.financetogether.ui.screens.users.registerScreen
import dev.financetogether.financetogether.ui.screens.users.userEmailCreationScreen
import dev.financetogether.financetogether.ui.screens.users.welcomeScreen
import getContributionViewModel
import getUserViewModel
import getGroupViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun appScaffold(navController: NavHostController) {
    val userViewModel = getUserViewModel()
    val groupViewModel = getGroupViewModel( userViewModel )
    val contributionViewModel = getContributionViewModel()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentUserEmail by userViewModel.currentUser.collectAsState(initial = null)
    val routesWithoutBottomBar = listOf("Splash", "Login", "Welcome", "SignInEmail", "UserEmailCreationScreen")
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (currentRoute == BottomNavItem.Groups.title) {
                MenuLateral(navController, groupViewModel, currentUserEmail?.email ?: "")
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentRoute == BottomNavItem.Groups.title) {
                    TopAppBar(
                        title = { Text("") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (currentRoute !in routesWithoutBottomBar) {
                    bottomNavigation(navController)
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "Splash",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("Splash") { splashScreen(navController, userViewModel) }
                composable("Welcome") { welcomeScreen(userViewModel, navController) }
                composable(BottomNavItem.Home.title) { homeScreen(userViewModel) }
                composable(BottomNavItem.Groups.title) { GroupsScreen(navController,groupViewModel) }
                composable("SignInEmail") { emailSignInScreen(navController, userViewModel) }
                composable("Register") { registerScreen(navController, userViewModel) }
                composable("CreateGroup") { CreateGroupScreen(navController, groupViewModel, userViewModel) }
                composable(BottomNavItem.Profile.title) { profileScreen(navController, userViewModel) }
                composable("EditProfile") { editProfileScreen(navController, userViewModel) }
                composable(
                    route = "UserEmailCreationScreen/{email}",
                    arguments = listOf(navArgument("email") { type = NavType.StringType }),
                ) { backStackEntry ->
                    userEmailCreationScreen(navController, userViewModel, backStackEntry.arguments?.getString("email")!!)
                }
                composable(
                    route = "AddMember/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val groupId = backStackEntry.arguments?.getString("groupId")!!
                    AddMemberScreen(navController, groupViewModel, groupId)
                }
                composable(
                    route = "EditGroup/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.StringType })
                ) { backStackEntry ->
                    EditGroupScreen(
                        navController = navController,
                        groupViewModel = groupViewModel,
                        groupId = backStackEntry.arguments?.getString("groupId") ?: ""
                    )
                }
                composable(
                    route = "GroupDetails/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val groupId = backStackEntry.arguments?.getString("groupId") ?: return@composable
                    GroupDetailsScreen(
                        navController = navController,
                        groupViewModel = groupViewModel,
                        groupId = groupId,
                        currentUserEmail = currentUserEmail?.email ?: ""
                    )
                }
                composable(
                    route = "AddContribution/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val groupId = backStackEntry.arguments?.getString("groupId") ?: return@composable
                    AddContributionScreen(navController, contributionViewModel, groupId)
                }
            }
        }
    }
}
