package com.kt.startkit.ui.features.main.root

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.kt.startkit.R
import com.kt.startkit.ui.res.IconRes
import com.kt.startkit.ui.res.IconResId

enum class RootTapBarItem(
    val selectedIcon: IconRes,
    val unselectedIcon: IconRes,
    val titleResId: Int,
    val route: NavigationRoute
) {
    MAP(
        selectedIcon = IconRes.DrawableResourceIcon(IconResId.map),
        unselectedIcon = IconRes.DrawableResourceIcon(IconResId.mapUnselected),
        titleResId = R.string.map,
        route = NavigationRoute.MAP,
    ),
    FAVORITE(
        selectedIcon = IconRes.DrawableResourceIcon(IconResId.favorite),
        unselectedIcon = IconRes.DrawableResourceIcon(IconResId.favoriteUnselected),
        titleResId = R.string.favorite,
        route = NavigationRoute.FAVORITE
    );

    companion object {
        fun items(): List<RootTapBarItem> {
            return values().asList()
        }
    }
}

@Composable
fun RootTapBar(
    tapBarItems: List<RootTapBarItem>,
    onNavigateToTap: (NavigationRoute) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
//        tonalElevation = 0.dp,
    ) {
        tapBarItems.forEach { item ->
            val selected = currentDestination.isMainTapInHierarchy(item)

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToTap(item.route) },
                icon = {
                    val icon = if (selected) {
                        item.selectedIcon
                    } else {
                        item.unselectedIcon
                    }
                    when (icon) {
                        is IconRes.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null,
                        )

                        is IconRes.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null,
                        )
                    }
                },
                modifier = modifier,
                enabled = true,
                label = { Text(stringResource(item.titleResId)) },
                alwaysShowLabel = true,
            )
        }
    }
}