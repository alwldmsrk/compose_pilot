package com.kt.startkit.ui.features.main.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kt.startkit.ui.features.main.LocalNavigationProvider
import com.kt.startkit.ui.features.main.favorite.FavoriteScreen
import com.kt.startkit.ui.features.main.map.MapScreen
import com.kt.startkit.ui.features.main.web.WebViewScreen



const val DETAIL_ARGUMENT_KEY = "url"
enum class NavigationRoute(val routeName: String) {
    MAP("/map_screen"),
    FAVORITE("/favorite"),
    FAVORITE_PLACE_URL("/detail?url={$DETAIL_ARGUMENT_KEY}"),
}

@Composable
fun RootNavHost() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.MAP.routeName,
    ) {
        mapScreen()
        favoriteScreen()
        favoriteDetailScreen()
    }
}

/**
 * Map Screen
 */
fun NavGraphBuilder.mapScreen() {
    composable(
        route = NavigationRoute.MAP.routeName,
    ) {
        MapScreen()
    }
}

/**
 * 즐겨찾기 Screen
 */
fun NavGraphBuilder.favoriteScreen() {
    composable(
        route = NavigationRoute.FAVORITE.routeName,
    ) {
        FavoriteScreen()
    }
}

/**
 * 즐겨찾기 Detail Screen
 */
fun NavGraphBuilder.favoriteDetailScreen() {
    composable(
        route = NavigationRoute.FAVORITE_PLACE_URL.routeName,
        arguments =
        listOf(navArgument(DETAIL_ARGUMENT_KEY) { type = NavType.StringType }),
    ) { backStackEntry ->
        backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)?.let { WebViewScreen(url = it) }
    }
}


/**
 * map Screen 으로 이동 (Default)
 */
fun NavController.navigateToMap(navOptions: NavOptions? = null) {
    navigate(NavigationRoute.MAP.routeName, navOptions)

    backQueue.lastOrNull()?.arguments?.apply {
        putString("name", "who a u?")
    }
}

/**
 * Favorite Screen 으로 이동 (Default)
 */
fun NavController.navigateToFavorite(navOptions: NavOptions? = null) {
    navigate(NavigationRoute.FAVORITE.routeName, navOptions)
}






