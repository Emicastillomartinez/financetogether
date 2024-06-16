package dev.financetogether.financetogether.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.GoogleAuthProvider
import dev.financetogether.financetogether.data.model.User
import dev.financetogether.financetogether.repository.firestore.FirestoreUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel(
    private val userRepository: FirestoreUserRepository,
) : ViewModel() {
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _emailExists = MutableStateFlow<Boolean?>(null)
    val emailExists: StateFlow<Boolean?> = _emailExists.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        checkCurrentUser()
    }

    fun checkCurrentUser() {
        viewModelScope.launch {
            val currentUser = userRepository.getCurrentUser()
            _isAuthenticated.value = currentUser != null
            currentUser?.let {
                val user = userRepository.getUser(it.uid)
                _currentUser.value = user
            }
        }
    }



    fun registerUserWithEmail(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            try {
                val emailExists = userRepository.emailExists(email)
                if (emailExists) {
                    _error.value = "El correo electrónico ya está registrado."
                } else {
                    val authResult = userRepository.firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                    authResult.user?.let { firebaseUser ->
                        val user = User(name = firstName, lastName = lastName, email = firebaseUser.email ?: "", uid = firebaseUser.uid)
                        userRepository.addUser(user)
                        _currentUser.value = user
                        _isAuthenticated.value = true
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error al registrar el usuario: ${e.localizedMessage}"
            }
        }
    }

    fun signInUserWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val authResult = userRepository.firebaseAuth.signInWithEmailAndPassword(email, password).await()
                authResult.user?.let {
                    _isAuthenticated.value = true
                    val user = userRepository.getUser(it.uid)
                    _currentUser.value = user
                    onResult(true)
                } ?: run {
                    _isAuthenticated.value = false
                    onResult(false)
                }
            } catch (e: Exception) {
                _error.value = "Error al iniciar sesión: ${e.localizedMessage}"
                _isAuthenticated.value = false
                onResult(false)
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        viewModelScope.launch {
            try {
                val authResult = userRepository.firebaseAuth.signInWithCredential(credential).await()
                authResult.user?.let { firebaseUser ->
                    val user = User(name = firebaseUser.displayName ?: "", email = firebaseUser.email ?: "")
                    if (!userRepository.emailExists(user.email)) {
                        userRepository.addUser(user)
                    }
                    _isAuthenticated.value = true // Usuario autenticado
                }
            } catch (e: Exception) {
                _error.value = "Error al iniciar sesión: ${e.localizedMessage}" // Manejo de errores
                _isAuthenticated.value = false
            }
        }
    }

    fun registerNewUser(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            try {
                val emailExists = userRepository.emailExists(email)
                if (emailExists) {
                    _error.value = "El correo electrónico ya está registrado."
                } else {
                    val authResult = userRepository.firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                    authResult.user?.let { firebaseUser ->
                        val user = User(name = "$firstName",  lastName = "$lastName", email = firebaseUser.email ?: "", uid = firebaseUser.uid)
                        userRepository.addUser(user)
                        _isAuthenticated.value = true
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error al registrar el usuario: ${e.localizedMessage}"
            }
        }
    }

    fun updateUserProfile(name: String, lastName: String, password: String,email: String) {
        viewModelScope.launch {
            try {
                val user = _currentUser.value
                if (user != null) {
                    val updatedUser = user.copy(name = name, lastName = lastName, email = email)
                    userRepository.updateUserProfile(updatedUser)
                    _currentUser.value = updatedUser
                    password?.let {
                        userRepository.updatePassword(it)
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error al actualizar el perfil: ${e.localizedMessage}"
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userRepository.signOut()
            _isAuthenticated.value = false
            _currentUser.value = null
        }
    }

    fun updateUserProfile(name: String, lastName: String, password: String) {
        viewModelScope.launch {
            try {
                val user = _currentUser.value
                if (user != null) {
                    val updatedUser = user.copy(name = name, lastName = lastName)
                    userRepository.updateUserProfile(updatedUser)
                    _currentUser.value = updatedUser
                }
            } catch (e: Exception) {
                _error.value = "Error al actualizar el perfil: ${e.localizedMessage}"
            }
        }
    }


    fun registerNewUser(
        email: String,
        password: String,
    ) {
    }
}