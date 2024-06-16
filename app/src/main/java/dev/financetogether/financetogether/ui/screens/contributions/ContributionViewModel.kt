package dev.financetogether.financetogether.ui.screens.contributions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.financetogether.financetogether.data.model.Contribution
import dev.financetogether.financetogether.repository.firestore.FirestoreContributionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContributionViewModel(
    private val contributionRepository: FirestoreContributionRepository
) : ViewModel() {

    private val _contributions = MutableStateFlow<List<Contribution>>(emptyList())
    val contributions: StateFlow<List<Contribution>> = _contributions.asStateFlow()

    fun loadContributions(groupId: String) {
        viewModelScope.launch {
            val contributions = contributionRepository.getContributions(groupId)
            _contributions.value = contributions
        }
    }

    fun addContribution(contribution: Contribution) {
        viewModelScope.launch {
            contributionRepository.addContribution(contribution)
            loadContributions(contribution.groupId) // Refresh the contributions list
        }
    }
}
