package com.kt.startkit.ui.features.main

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavigationProvider =
    staticCompositionLocalOf<NavHostController> { error("No navigation host controller provided.") }

//val LocalColorsProvider = staticCompositionLocalOf { MyReadingColors() }
//
//val LocalTypographyProvider = staticCompositionLocalOf { MyReadingTypography() }
