package com.kt.startkit.ui.features.main.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions

@Composable
fun NavHostController.currentDestinationAsState(): NavDestination? =
    currentBackStackEntryAsState().value?.destination

@Composable
fun NavHostController.currentRootTapBarItem(): RootTapBarItem? {
//    return when (currentDestination?.route) {
    return when (currentDestinationAsState()?.route) {
        NavigationRoute.HOME.routeName -> RootTapBarItem.HOME
        NavigationRoute.SETTING.routeName -> RootTapBarItem.SETTING
        else -> null
    }
}

fun NavHostController.navigateToMainTap(route: NavigationRoute) {
    val topLevelNavOptions = navOptions {
        // Pop up to the start destination of the graph to

        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

    when (route) {
        NavigationRoute.HOME -> navigateToHome(topLevelNavOptions)
        NavigationRoute.SETTING_GRAPH -> navigateToSetting(topLevelNavOptions)
        else -> {} // main tap 외의 route 는 무시한다.
    }
}

/// 현재의 route path 에 입력한 탭이 포함되어 있는지 확인한다.
fun NavDestination?.isMainTapInHierarchy(rootTapBarItem: RootTapBarItem) =
    this?.hierarchy?.any {
        it.route?.contains(rootTapBarItem.route.routeName, true) ?: false
    } ?: false