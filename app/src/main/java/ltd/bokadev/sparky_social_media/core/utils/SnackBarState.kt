package ltd.bokadev.sparky_social_media.core.utils

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackBarState constructor(
    val scaffoldState: ScaffoldState,
    val scope: CoroutineScope,
    val navController: NavHostController
) {

    private var snackBarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun showSnackBar(
        message: String
    ) {
        if (snackBarJob == null) {
            snackBarJob = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long,
                    actionLabel = "OK"
                )
                cancelActiveJob()
            }
        } else {
            cancelActiveJob()
            snackBarJob = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long,
                    actionLabel = "OK"
                )
                cancelActiveJob()
            }
        }
    }

    private fun cancelActiveJob() {
        snackBarJob?.let { job ->
            job.cancel()
            snackBarJob = Job()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(
        snackbarHostState = remember {
            SnackbarHostState()
        }
    ),
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, scope) {
    SnackBarState(
        scaffoldState = scaffoldState,
        navController = navController,
        scope = scope
    )
}