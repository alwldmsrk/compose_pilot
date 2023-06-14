package com.kt.startkit.ui.features.main.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.kt.startkit.ui.features.main.LocalNavigationProvider
import com.kt.startkit.ui.features.main.favorite.FavoriteScreen
import com.kt.startkit.ui.features.main.map.MapScreen

enum class NavigationRoute(val routeName: String) {
    MAP("/map_screen"),
    FAVORITE_GRAPH("/favorite"),

    FAVORITE("/favorite/root"),
    FAVORITE_PLACE_URL("/favorite/place_url?url={url}"),
}

@Composable
fun RootNavHost() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.MAP.routeName,
    ) {
        mapScreen()
        favoriteScreen(navController = navController)
        favoriteDetailScreen(navController = navController)
    }
}

fun NavGraphBuilder.mapScreen() {
    composable(
        route = NavigationRoute.MAP.routeName,
    ) {
        MapScreen()
    }
}

fun NavGraphBuilder.favoriteScreen(navController: NavController) {
    composable(
        route = NavigationRoute.FAVORITE.routeName,
    ) {
        FavoriteScreen(
            onItemClick = { route ->
                navController.navigateToFavoriteDetailScreen(route)
            },
        )
    }
}

fun NavGraphBuilder.favoriteDetailScreen(navController: NavController) {
    composable(
        route = NavigationRoute.FAVORITE_PLACE_URL.routeName,
        arguments =
        listOf(navArgument("url") { type = NavType.StringType }),

        ) {
//        NoticeScreen(
//            onBackClick = onBackClick
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


fun NavController.navigateToFavoriteDetailScreen(navOptions: NavOptions? = null) {
    navigate(NavigationRoute.FAVORITE_PLACE_URL.routeName, navOptions)
}





