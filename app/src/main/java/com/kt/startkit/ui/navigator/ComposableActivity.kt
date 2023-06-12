package com.kt.startkit.ui.navigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kt.startkit.ui.features.main.root.RootScreen
import com.kt.startkit.ui.features.start.StartScreen
import com.kt.startkit.ui.theme.StartKitTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ComposableActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StartKitTheme {
                AppNavHost()
            }
        }
    }
}
