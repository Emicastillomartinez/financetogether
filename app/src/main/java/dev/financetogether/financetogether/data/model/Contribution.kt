package dev.financetogether.financetogether.data.model

data class Contribution(
    val id: String = "",
    val groupId: String = "",
    val userEmail: String = "",
    val userName: String = "",
    val amount: Double = 0.0,
    val date: Long = System.currentTimeMillis()
)