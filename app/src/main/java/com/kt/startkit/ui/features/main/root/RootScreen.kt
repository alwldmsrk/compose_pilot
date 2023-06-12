package com.kt.startkit.ui.features.main.root

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kt.startkit.ui.features.main.LocalNavigationProvider


@Composable
fun RootScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: RootScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    when (state) {
        is RootViewState.Initial -> {
            viewModel.observeUserProfile()
        }

        is RootViewState.Data -> {
            CompositionLocalProvider(LocalNavigationProvider provides navController) {
                RootContentView()
            }
        }

        is RootViewState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.material.Text(
                    (state as RootViewState.Error).message,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContentView() {
    val navController = LocalNavigationProvider.current

    Scaffold(
        bottomBar = {
            RootTapBar(
                tapBarItems = RootTapBarItem.items(),
                onNavigateToTap = navController::navigateToMainTap,
                currentDestination = navController.currentDestination,
            )
        },
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
//                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            Column(Modifier.fillMaxSize()) {
                // Show the top app bar on top level destinations.
                val destination = navController.currentRootTapBarItem()
                if (destination != null) {
                    CenterAlignedTopAppBar(
                        title = { Text(text = stringResource(id = destination.titleResId)) },
//                        navigationIcon = {
//                            IconButton(onClick = onNavigationClick) {
//                                Icon(
//                                    imageVector = navigationIcon,
//                                    contentDescription = navigationIconContentDescription,
//                                    tint = MaterialTheme.colorScheme.onSurface,
//                                )
//                            }
//                        },
//                        actions = {
//                            IconButton(onClick = onActionClick) {
//                                Icon(
//                                    imageVector = actionIcon,
//                                    contentDescription = actionIconContentDescription,
//                                    tint = MaterialTheme.colorScheme.onSurface,
//                                )
//                            }
//                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                    )
                }

                RootNavHost()
            }
        }
    }
}







