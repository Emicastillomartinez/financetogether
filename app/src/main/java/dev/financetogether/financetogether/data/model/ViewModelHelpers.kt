// ViewModelHelpers.kt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.firestore.FirebaseFirestore
import dev.financetogether.financetogether.repository.firestore.FirestoreContributionRepository
import dev.financetogether.financetogether.repository.firestore.FirestoreUserRepository
import dev.financetogether.financetogether.repository.firestore.FirestoreGroupRepository
import dev.financetogether.financetogether.ui.screens.contributions.ContributionViewModel
import dev.financetogether.financetogether.ui.screens.users.UserViewModel
import dev.financetogether.financetogether.ui.screens.groups.GroupViewModel

@Composable
fun getUserViewModel(): UserViewModel {
    val context = LocalContext.current
    val userRepository = FirestoreUserRepository(FirebaseFirestore.getInstance())

    return ViewModelProvider(
        context as ViewModelStoreOwner,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                    return UserViewModel(userRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        },
    )[UserViewModel::class.java]
}

@Composable
fun getGroupViewModel(userViewModel: UserViewModel): GroupViewModel {
    val context = LocalContext.current
    val groupRepository = FirestoreGroupRepository(FirebaseFirestore.getInstance())

    return ViewModelProvider(
        context as ViewModelStoreOwner,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                if (modelClass.isAssignableFrom(GroupViewModel::class.java)) {
                    return GroupViewModel(groupRepository, userViewModel) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        },
    )[GroupViewModel::class.java]
}
@Composable
fun getContributionViewModel(): ContributionViewModel {
    val context = LocalContext.current
    val contributionRepository = FirestoreContributionRepository(FirebaseFirestore.getInstance())

    return ViewModelProvider(
        context as ViewModelStoreOwner,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                if (modelClass.isAssignableFrom(ContributionViewModel::class.java)) {
                    return ContributionViewModel(contributionRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        },
    )[ContributionViewModel::class.java]
}
