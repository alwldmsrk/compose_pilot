package com.kt.startkit.ui.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kt.startkit.ui.features.main.LocalNavigationProvider
import com.kt.startkit.ui.features.main.root.RootScreen
import com.kt.startkit.ui.features.start.StartScreen


enum class AppNavigationRoute(val routeName: String) {
    START("/start"),
    ROOT("/root")
}

fun NavHostController.navigate(route: AppNavigationRoute, option: (NavOptionsBuilder.(NavHostController) -> Unit)? = null) {
    navigate(route.routeName) {
        option?.let {
            it(this@navigate)
        }
    }
}

@Composable
fun AppNavigationRoute.screen(controller: NavHostController,
                              backstackEntry: NavBackStackEntry) {
    when(this) {
        AppNavigationRoute.START -> {
            StartScreen()
        }
        AppNavigationRoute.ROOT -> {
//            val backStackEntry = remember(backstackEntry) {
//                controller.getBackStackEntry(prevBackStackRouteName)
//            }
//
            /// TEST
//            val screenViewModel: StartScreenViewModel = hiltViewModel(backStackEntry)
            //// inject
//            RootScreen(screenViewModel= screenViewModel)
            RootScreen()
        }
    }
}


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    CompositionLocalProvider(LocalNavigationProvider provides navController) {
        NavHost(navController = navController,
            startDestination = AppNavigationRoute.START.routeName) {
            AppNavigationRoute.values().forEach {
                screen(navController, it)
            }
        }
    }
}

fun NavGraphBuilder.screen(controller: NavHostController, route: AppNavigationRoute) {
    composable(
        route = route.routeName
    ) {
        route.screen(controller, it)
    }
}

