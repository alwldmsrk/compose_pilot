package com.kt.startkit.ui.features.start

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.kt.startkit.core.base.StateViewModelListener
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.core.permission.PermissionUtil
import com.kt.startkit.ui.features.main.LocalNavigationProvider
import com.kt.startkit.ui.navigator.AppNavigationRoute
import com.kt.startkit.ui.navigator.navigate


@OptIn(ExperimentalPermissionsApi::class)
@NonRestartableComposable
@Composable
fun StartScreen(screenViewModel: StartScreenViewModel = hiltViewModel()) {
    val activity = (LocalContext.current as? Activity)
    val navController = LocalNavigationProvider.current

    val permissionState = rememberMultiplePermissionsState(permissions = PermissionUtil.permissions) {
        screenViewModel.fetchInitialData(needPermissionCheck = false)
    }

    LaunchedEffect(key1 = "", block = {
        screenViewModel.fetchInitialData(needPermissionCheck = !permissionState.allPermissionsGranted)
    })

    StateViewModelListener(stateViewModel = screenViewModel, listen = {
        when(it) {
            is StartScreenState.FailToInitialize -> {
                // 앱 실행 실패 팝업
                Logger.e("Fail to start App!!")
                activity?.finish()
            }
            is StartScreenState.ShouldAppUpdate -> {
                // 강제 업데이트 팝업
                activity?.finish()
            }
            is StartScreenState.NavigateToMain -> {
                Logger.d("start main activity~~")
                /// route
                navController.navigate(route = AppNavigationRoute.ROOT) {
                    navController.popBackStack()
                    it.graph.setStartDestination(AppNavigationRoute.ROOT.routeName)
                }
            }
            is StartScreenState.NeedToLogin -> {
                // TODO: show login activity
                activity?.finish()
            }
            is StartScreenState.NeedToOnboarding -> {
                // TODO: show onboarding activity
                activity?.finish()
            }
            is StartScreenState.CheckPermission -> {
                permissionState.launchMultiplePermissionRequest()
            }
            else -> {}
        }
    })

    StartScreenCompose()
}

@Composable
fun StartScreenCompose() {
    Box (
        modifier= Modifier.fillMaxSize()
    ){
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
