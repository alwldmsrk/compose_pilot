package com.kt.startkit.ui.features.start

import com.kt.startkit.core.base.ViewState


// State Hierarchy
//
// StartScreenState
//    |- Loading: 초기 상태
//    |- FailToInitialize : 서버 데이터 패치 오류
//    |- ShouldAppUpdate : 강제 업데이트 필요
//    |- Success : splash 종료 후 다음 화면으로 이동 <abstract>
//        |- NeedToLogin : 로그인 화면 이동
//        |- NeedToOnboarding : 온보딩 화면 이동
//        |- NavigateToMain : 메인 화면 이동

sealed class StartScreenState : ViewState() {
    object Loading : StartScreenState()
    object FailToInitialize : StartScreenState()
    object ShouldAppUpdate : StartScreenState()

    abstract class Success : StartScreenState()

    object NeedToLogin : Success()
    object NeedToOnboarding : Success()
    data class NavigateToMain(val bannerPolicy: Boolean) : Success()
}

