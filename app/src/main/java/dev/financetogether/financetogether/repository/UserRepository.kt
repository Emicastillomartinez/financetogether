package dev.financetogether.financetogether.data.repository

import dev.financetogether.financetogether.data.model.User

interface UserRepository {
    suspend fun addUser(user: User)

    suspend fun getUsers(): List<User>

    suspend fun emailExists(email: String): Boolean
}
