// FirestoreContributionRepository.kt
package dev.financetogether.financetogether.repository.firestore

import com.google.firebase.firestore.FirebaseFirestore
import dev.financetogether.financetogether.data.model.Contribution
import kotlinx.coroutines.tasks.await

class FirestoreContributionRepository(private val db: FirebaseFirestore) {

    suspend fun getContributions(groupId: String): List<Contribution> {
        return db.collection("contributions")
            .whereEqualTo("groupId", groupId)
            .get()
            .await()
            .toObjects(Contribution::class.java)
    }

    suspend fun addContribution(contribution: Contribution) {
        db.collection("contributions").add(contribution).await()
    }

    suspend fun getTotalContributions(groupId: String): Double {
        val contributions = getContributions(groupId)
        return contributions.sumOf { it.amount }
    }
}