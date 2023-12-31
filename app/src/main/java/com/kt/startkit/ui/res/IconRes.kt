package com.kt.startkit.ui.res

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.kt.startkit.R

object IconResId {
    const val map = R.drawable.baseline_location_on_black_24
    const val mapUnselected = R.drawable.outline_location_on_black_24

    const val favorite = R.drawable.outline_favorite_black_24
    const val favoriteUnselected = R.drawable.outline_favorite_border_black_24

    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.Rounded.ArrowBack
    val Search = Icons.Rounded.Search
}

sealed class IconRes {
    data class ImageVectorIcon(val imageVector: ImageVector) : IconRes()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : IconRes()
}