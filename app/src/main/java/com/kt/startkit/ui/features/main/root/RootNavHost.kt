package com.kt.startkit.ui.features.main.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kt.startkit.ui.features.main.LocalNavigationProvider
import com.kt.startkit.ui.features.main.home.HomeScreen
import com.kt.startkit.ui.features.main.setting.SettingScreen
import com.kt.startkit.ui.features.main.setting.notice.NoticeScreen

enum class NavigationRoute(val routeName: String) {
    HOME("/home_screen"),

    SETTING_GRAPH("/setting"),
    SETTING("/setting/root"),
    SETTING_PROFILE_NAME("/setting/profile_name")
}

@Composable
fun RootNavHost() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HOME.routeName,
    ) {
        homeScreen()
        settingGraph(navController = navController)
    }
}


/// HomeScreen
fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(NavigationRoute.HOME.routeName, navOptions)

    backQueue.lastOrNull()?.arguments?.apply {
        putString("name", "who a u?")
    }
}

fun NavGraphBuilder.homeScreen() {
    composable(
        route = NavigationRoute.HOME.routeName,
    ) {
        HomeScreen()
    }
}

/// SettingScreen
fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(NavigationRoute.SETTING_GRAPH.routeName, navOptions)
}

fun NavGraphBuilder.settingGraph(
    navController: NavController,
) {
    navigation(
        route = NavigationRoute.SETTING_GRAPH.routeName,
        startDestination = NavigationRoute.SETTING.routeName,
    ) {

        composable(route = NavigationRoute.SETTING.routeName) {
            SettingScreen(
                onItemClick = { route ->
                    navController.navigateToSettingItem(route)
                },
            )
        }
        composable(route = NavigationRoute.SETTING_PROFILE_NAME.routeName) {
            NoticeScreen(
                onBackClick = navController::popBackStack
            )
        }
    }
}

fun NavController.navigateToSettingItem(route: String) {
//    val encodedId = Uri.encode(itemId)
//    this.navigate("topic_route/$encodedId")
//    this.navigate("setting/$itemId")
    this.navigate(route)
}

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




