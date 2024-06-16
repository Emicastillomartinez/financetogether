@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.financetogether.financetogether
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import dev.financetogether.financetogether.components.nav.appScaffold
import dev.financetogether.financetogether.ui.theme.FinanceTogetherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceTogetherTheme {
                val navController = rememberNavController()

                appScaffold(navController)
            }
        }
    }
}
