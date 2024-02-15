package ltd.bokadev.sparky_social_media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.graphs.SparkyNavigation
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.core.utils.rememberAppState
import ltd.bokadev.sparky_social_media.ui.theme.SparkyAppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb(),
            ), navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb(),
            )
        )
        setContent {
            SparkyAppTheme {
                val appState = rememberAppState()
                val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
                // A surface container using the 'background' color from the theme
                Scaffold(snackbarHost = CustomModifiers.snackBarHost) { innerPadding ->
                    SparkyNavigation(
                        navController = appState.navController,
                        navigator = navigator,
                        showSnackBar = { message ->
                            appState.showSnackBar(message)
                        }
                    )
                }
            }
        }
    }
}