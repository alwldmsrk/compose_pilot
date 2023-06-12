package com.kt.startkit.ui.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import com.kt.startkit.ui.features.main.root.RootScreen
import com.kt.startkit.ui.theme.StartKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StartKitTheme {
                RootScreen()
            }
        }
    }
}