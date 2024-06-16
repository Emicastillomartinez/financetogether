package dev.financetogether.financetogether.repository.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.financetogether.financetogether.data.model.User
import dev.financetogether.financetogether.data.repository.UserRepository
import kotlinx.coroutines.tasks.await

class FirestoreUserRepository(private val db: FirebaseFirestore) : UserRepository {
    val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun addUser(user: User) {
        db.collection("users").add(user).await()
    }

    override suspend fun getUsers(): List<User> {
        return db.collection("users").get().await().toObjects(User::class.java)
    }

    override suspend fun emailExists(email: String): Boolean {
        val querySnapshot =
            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .await()
        return !querySnapshot.isEmpty // Retorna true si encuentra documentos con ese email
    }

    suspend fun updateUserProfile(user: User) {
        val userDocument = db.collection("users").whereEqualTo("email", user.email).get().await()
        if (!userDocument.isEmpty) {
            val documentId = userDocument.documents[0].id
            db.collection("users").document(documentId).set(user).await()
        } else {
            throw Exception("User not found")
        }
    }

    suspend fun updatePassword(newPassword: String) {
        val currentUser = firebaseAuth.currentUser
        currentUser?.updatePassword(newPassword)?.await()
    }
    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    suspend fun getUser(uid: String): User? {
        val querySnapshot = db.collection("users").whereEqualTo("uid", uid).get().await()
        val document = querySnapshot.documents.firstOrNull()
        return document?.toObject(User::class.java)
    }
}