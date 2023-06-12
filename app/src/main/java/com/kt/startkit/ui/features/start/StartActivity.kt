package com.kt.startkit.ui.features.start

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.ui.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StartActivity : ComponentActivity() {

//    @Inject
//    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: StartActivityViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState
                    .onEach {
                        didChangeViewState(it)
                    }
                    .collect()
//            }

        }

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                // Success 상태가 아니면 splash 닫지 않고 처리한다.
                // ex) 강제 업데이트, 필수 데이터 수신 에러 등
                viewModel.viewState.value !is StartActivityState.Success
            }
        }

        viewModel.fetchInitialData()

//        setContent {
//            StartKitTheme {
////                CompositionLocalProvider(LocalMainState provides state) {
////                    ContentView((state as MainViewState.Data).items)
////                }
//                RootScreen()
//            }
//        }
    }

    private fun didChangeViewState(state: StartActivityState) {
        when (state) {
            is StartActivityState.FailToInitialize -> {
                // 앱 실행 실패 팝업
                Logger.e("Fail to start App!!")
                finish()
            }
            is StartActivityState.ShouldAppUpdate -> {
                // 강제 업데이트 팝업
                finish()
            }
            is StartActivityState.NavigateToMain -> {
                Logger.d("start main activity~~")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            is StartActivityState.NeedToLogin -> {
                // TODO: show login activity
                finish()
            }
            is StartActivityState.NeedToOnboarding -> {
                // TODO: show onboarding activity
                finish()
            }
            else -> {}

        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


