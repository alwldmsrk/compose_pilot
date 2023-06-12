package com.kt.startkit.ui.features.main.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kt.startkit.ui.features.main.LocalNavigationProvider
import com.kt.startkit.ui.features.main.favorite.FavoriteScreen
import com.kt.startkit.ui.features.main.map.MapScreen

enum class NavigationRoute(val routeName: String) {
    MAP("/map_screen"),

    //    SETTING_GRAPH("/setting"),
    FAVORITE("/favorite"),
//    SETTING_PROFILE_NAME("/setting/profile_name")
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
                navController.navigateToFavoriteItem(route)
            },
        )
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

fun NavController.navigateToFavoriteItem(route: String) {
//    val encodedId = Uri.encode(itemId)
//    this.navigate("topic_route/$encodedId")
//    this.navigate("setting/$itemId")
    this.navigate(route)
}


//
//fun NavGraphBuilder.settingGraph(
//    navController: NavController,
//) {
//    navigation(
//        route = NavigationRoute.FAVORITE.routeName,
//        startDestination = NavigationRoute.FAVORITE.routeName,
//    ) {
//
//        composable(route = NavigationRoute.FAVORITE.routeName) {
//            FavoriteScreen(
//                onItemClick = { route ->
//                    navController.navigateToFavoriteItem(route)
//                },
//            )
//        }
////        composable(route = NavigationRoute.FAVORITE.routeName) {
////            NoticeScreen(
////                onBackClick = navController::popBackStack
////            )
////        }
//    }
//}


//fun NavGraphBuilder.settingItemScreen(
//    onBackClick: () -> Unit,
////    onItemClick: (String) -> Unit,
//) {
//    composable(
//        route = NavigationRoute.SETTING_PROFILE_NAME.routeName,
////        route = "topic_route/{$topicIdArg}",
////        arguments = listOf(
////            navArgument(topicIdArg) { type = NavType.StringType },
////        ),
//    ) {
//        NoticeScreen(
//            onBackClick = onBackClick
//        )
//    }
//}




