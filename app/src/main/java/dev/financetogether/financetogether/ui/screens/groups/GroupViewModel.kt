package dev.financetogether.financetogether.ui.screens.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.financetogether.financetogether.data.model.Contribution
import dev.financetogether.financetogether.data.model.Group
import dev.financetogether.financetogether.data.model.User
import dev.financetogether.financetogether.repository.firestore.FirestoreGroupRepository
import dev.financetogether.financetogether.ui.screens.users.UserViewModel
import dev.financetogether.financetogether.util.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroupViewModel(
    private val groupRepository: FirestoreGroupRepository,
    private val userViewModel: UserViewModel
) : ViewModel() {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()
    private val _userByEmail = MutableStateFlow<User?>(null)
    val userByEmail: StateFlow<User?> = _userByEmail.asStateFlow()

    private val _contributions = MutableStateFlow<List<Contribution>>(emptyList())
    val contributions: StateFlow<List<Contribution>> = _contributions.asStateFlow()
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    private val _totalContributions = MutableStateFlow<Double>(0.0)
    val totalContributions: StateFlow<Double> = _totalContributions.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    init {
        loadGroups()
    }
    fun deleteGroup(groupId: String) {
        viewModelScope.launch {
            groupRepository.deleteGroup(groupId)
            loadGroups() // Refresh the group list
        }
    }

    private fun loadGroups() {
        viewModelScope.launch {
            val groups = groupRepository.getGroups()
            _groups.value = groups
        }
    }

    fun addMemberToGroup(groupId: String, memberEmail: String) {
        viewModelScope.launch {
            if (!Validator.validateEmail(memberEmail)) {
                _emailError.value = "El formato del correo electrónico no es válido."
                return@launch
            }
            val user = groupRepository.getUserByEmail(memberEmail)
            if (user == null) {
                _emailError.value = "El usuario no existe. Por favor, regístrese primero."
                return@launch
            }
            groupRepository.addMemberToGroup(groupId, memberEmail)
            loadGroups() // Refresh the group list
            _emailError.value = null // Clear any previous error
        }
    }

    fun updateGroup(groupId: String, newName: String, newDescription: String, newMembers: List<String>) {
        viewModelScope.launch {
            groupRepository.updateGroup(groupId, newName, newDescription, newMembers)
            loadGroups() // Refresh the group list
        }
    }

    fun getGroupById(groupId: String): StateFlow<Group?> {
        val groupFlow = MutableStateFlow<Group?>(null)
        viewModelScope.launch {
            val group = groupRepository.getGroup(groupId)
            groupFlow.value = group
        }
        return groupFlow.asStateFlow()
    }

    fun createGroup(group: Group) {
        viewModelScope.launch {
            groupRepository.createGroup(group)
            loadGroups() // Refresh the group list
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            val user = groupRepository.getUserByEmail(email)
            _userByEmail.value = user
        }
    }

    fun removeMemberFromGroup(groupId: String, memberEmail: String) {
        viewModelScope.launch {
            groupRepository.removeMemberFromGroup(groupId, memberEmail)
            loadGroups() // Refresh the group list
        }
    }

    fun addContribution(groupId: String, amount: Double) {
        viewModelScope.launch {
            val user = userViewModel.currentUser.value
            if (user != null) {
                val contribution = Contribution(
                    groupId = groupId,
                    userEmail = user.email,
                    userName = user.name,
                    amount = amount
                )
                groupRepository.addContribution(groupId, contribution)
                loadContributions(groupId) // Refresh the contributions list
                _totalContributions.value = getTotalContributions(groupId).value // Update total contributions
            }
        }
    }

    fun loadContributions(groupId: String) {
        viewModelScope.launch {
            val contributions = groupRepository.getContributions(groupId)
            _contributions.value = contributions
            _totalContributions.value = contributions.sumOf { it.amount } // Update total contributions
        }
    }

    fun getTotalContributions(groupId: String): StateFlow<Double> {
        val totalFlow = MutableStateFlow(0.0)
        viewModelScope.launch {
            val total = groupRepository.getTotalContributions(groupId)
            totalFlow.value = total
        }
        return totalFlow.asStateFlow()
    }
}
