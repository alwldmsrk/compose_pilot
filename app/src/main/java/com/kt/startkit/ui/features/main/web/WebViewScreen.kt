package com.kt.startkit.ui.features.main.web

import androidx.compose.runtime.Composable
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.kt.startkit.core.logger.Logger

@Composable
fun WebViewScreen(
    url: String = "http://place.map.kakao.com/16618597"
) {
    Logger.i("start web view")
    val state = rememberWebViewState(url)
    WebView(
        state = state,
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