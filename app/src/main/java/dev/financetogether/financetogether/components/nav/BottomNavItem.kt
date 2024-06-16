package dev.financetogether.financetogether.components.nav

import dev.financetogether.financetogether.R

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
) {
    object Home : BottomNavItem("Home", R.drawable.ic_home)
    object Groups : BottomNavItem("Groups", R.drawable.ic_groups)
    object Profile : BottomNavItem("Profile", R.drawable.ic_profile)
}