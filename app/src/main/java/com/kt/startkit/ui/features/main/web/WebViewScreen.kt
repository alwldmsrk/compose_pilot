package com.kt.startkit.ui.features.main.web

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState


@Composable
fun WebViewScreen(
    url: String
) {
    val webViewState = rememberWebViewState(url)
    val webViewNavigator = rememberWebViewNavigator()

    WebView(
        state = webViewState,
        onCreated = { webView ->
            with(webView) {
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    javaScriptCanOpenWindowsAutomatically = false
                }
            }
        }
    )
}