package ltd.bokadev.sparky_social_media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.navigation.graphs.SparkyNavigation
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.core.utils.hideKeyboard
import ltd.bokadev.sparky_social_media.core.utils.rememberAppState
import ltd.bokadev.sparky_social_media.data.BottomNavItem
import ltd.bokadev.sparky_social_media.presentation.MainEvent
import ltd.bokadev.sparky_social_media.presentation.MainViewModel
import ltd.bokadev.sparky_social_media.presentation.home_screen.CreatePostBottomSheet
import ltd.bokadev.sparky_social_media.ui.theme.SparkyAppTheme
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    internal lateinit var navigator: Navigator

    @OptIn(ExperimentalMaterialApi::class)
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
                var shouldShowBottomNavigation by remember { (mutableStateOf(false)) }
                val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
                val scope = rememberCoroutineScope()

                //I know you pointed out my naming for these, here I use bottomSheetState because of the valu wrapper ModalBottomSheetValue
                val bottomSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
                )

                when (navBackStackEntry?.destination?.route) {
                    Screen.HomeScreen.route -> {
                        shouldShowBottomNavigation = true
                    }

                    Screen.SearchScreen.route -> {
                        shouldShowBottomNavigation = true
                    }

                    Screen.ProfileScreen.route -> {
                        shouldShowBottomNavigation = true
                    }

                    else -> {
                        shouldShowBottomNavigation = false
                    }
                }

                LaunchedEffect(key1 = viewModel.state.shouldCloseBottomSheet) {
                    if (viewModel.state.shouldCloseBottomSheet) {
                        scope.launch {
                            bottomSheetState.hide()
                        }
                        viewModel.resetShouldCloseBottomSheet()
                    }
                }

                ModalBottomSheetLayout(
                    modifier = Modifier.hideKeyboard(LocalFocusManager.current),
                    sheetContent = {
                        CreatePostBottomSheet(viewModel = viewModel, onCloseClick = {
                            viewModel.resetMessage()
                            scope.launch {
                                bottomSheetState.hide()
                            }
                        }) {
                            viewModel.onEvent(MainEvent.OnCreatePostClick)
                        }
                    },
                    sheetState = bottomSheetState,
                    sheetShape = RoundedCornerShape(
                        topStart = 20.dp, topEnd = 20.dp
                    ),
                    scrimColor = SparkyTheme.colors.primaryColor.copy(alpha = .5f),
                    sheetBackgroundColor = SparkyTheme.colors.primaryColor
                ) {
                    Scaffold(snackbarHost = {
                        CustomModifiers.snackBarHost(appState.scaffoldState)
                    }, floatingActionButton = {
                        if (shouldShowBottomNavigation) FloatingActionButton(
                            onClick = {
                                scope.launch {
                                    bottomSheetState.show()
                                }
                            },
                            modifier = Modifier
                                .size(50.dp)
                                .offset(y = 80.dp)
                                .clip(CircleShape),
                            containerColor = SparkyTheme.colors.yellow
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    }, floatingActionButtonPosition = FabPosition.Center, bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Posts",
                                    route = Screen.HomeScreen.route,
                                    icon = R.drawable.ic_home
                                ), BottomNavItem(
                                    name = "Profile",
                                    route = Screen.ProfileScreen.route,
                                    icon = R.drawable.ic_person
                                )
                            ),
                            navController = appState.navController,
                            onItemClick = {
                                appState.navController.navigate(it.route) {
                                    popUpTo(Screen.HomeScreen.route) {
                                        inclusive = it.route == Screen.HomeScreen.route
                                    }
                                }
                            },
                            bottomBarState = shouldShowBottomNavigation,
                            modifier = Modifier
                                .hideKeyboard(LocalFocusManager.current)
                                .navigationBarsPadding()
                                .background(SparkyTheme.colors.primaryColor)
                                .height(80.dp)
                                .clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
                        )
                    }) { innerPadding ->
                        SparkyNavigation(navController = appState.navController,
                            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                            navigator = navigator,
                            showSnackBar = { message ->
                                appState.showSnackBar(message)
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
    bottomBarState: Boolean = false
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = SparkyTheme.colors.primaryColor,
            elevation = 0.dp,
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(selected = selected,
                    onClick = { onItemClick(item) },
                    selectedContentColor = SparkyTheme.colors.primaryColor,
                    unselectedContentColor = SparkyTheme.colors.bottomNavigationUnselected,
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.name,
                            tint = if (selected) SparkyTheme.colors.white else SparkyTheme.colors.bottomNavigationUnselected
                        )
                    },
                    label = {
                        Text(
                            text = item.name,
                            color = if (selected) SparkyTheme.colors.white else SparkyTheme.colors.bottomNavigationUnselected,
                            style = SparkyTheme.typography.poppinsRegular12
                        )
                    })
            }
        }
    }
}