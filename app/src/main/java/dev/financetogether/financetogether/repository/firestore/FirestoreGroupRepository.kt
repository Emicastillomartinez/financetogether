package dev.financetogether.financetogether.repository.firestore

import com.google.firebase.firestore.FirebaseFirestore
import dev.financetogether.financetogether.data.model.Contribution
import dev.financetogether.financetogether.data.model.Group
import dev.financetogether.financetogether.data.model.User
import kotlinx.coroutines.tasks.await

class FirestoreGroupRepository(private val db: FirebaseFirestore) {

    suspend fun getGroups(): List<Group> {
        val snapshot = db.collection("groups").get().await()
        return snapshot.documents.mapNotNull { document ->
            document.toObject(Group::class.java)?.apply { id = document.id }
        }
    }

    suspend fun getGroup(groupId: String): Group? {
        val document = db.collection("groups").document(groupId).get().await()
        return document.toObject(Group::class.java)
    }

    suspend fun createGroup(group: Group) {
        db.collection("groups").add(group).await()
    }

    suspend fun updateGroup(groupId: String, newName: String, newDescription:String , newMembers: List<String>) {
        db.collection("groups").document(groupId).update(mapOf(
            "name" to newName,
            "description" to newDescription,
            "members" to newMembers
        )).await()
    }


    suspend fun getUserByEmail(email: String): User? {
        val querySnapshot = db.collection("users").whereEqualTo("email", email).get().await()
        return if (querySnapshot.documents.isNotEmpty()) {
            querySnapshot.documents[0].toObject(User::class.java)
        } else {
            null
        }
    }

    suspend fun removeMemberFromGroup(groupId: String, memberEmail: String) {
        val groupDocument = db.collection("groups").document(groupId).get().await()
        val group = groupDocument.toObject(Group::class.java)
        group?.let {
            val updatedMembers = it.members.toMutableList().apply { remove(memberEmail) }
            db.collection("groups").document(groupId).update("members", updatedMembers).await()
        }
    }

    suspend fun addContribution(groupId: String, contribution: Contribution) {
        val groupDocument = db.collection("groups").document(groupId).get().await()
        val group = groupDocument.toObject(Group::class.java)
        group?.let {
            db.collection("groups").document(groupId).collection("contributions").add(contribution).await()
        }
    }
    suspend fun addMemberToGroup(groupId: String, memberEmail: String) {
        val groupDocument = db.collection("groups").document(groupId).get().await()
        val group = groupDocument.toObject(Group::class.java)
        group?.let {
            val updatedMembers = it.members.toMutableList().apply { add(memberEmail) }
            db.collection("groups").document(groupId).update("members", updatedMembers).await()
        }
    }
    suspend fun getTotalContributions(groupId: String): Double {
        val contributions = getContributions(groupId)
        return contributions.sumOf { it.amount }
    }

    suspend fun getContributions(groupId: String): List<Contribution> {
        return db.collection("groups").document(groupId).collection("contributions").get().await().toObjects(Contribution::class.java)
    }
}
