package dev.financetogether.financetogether.data.model

data class Group(
    var id: String = "",
    val name: String = "",
    val members: List<String> = emptyList(),
    var description: String = "",
    val contributions: List<Contribution> = listOf(),
    val adminEmail: String = ""
)