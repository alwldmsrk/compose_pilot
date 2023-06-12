package com.kt.startkit.ui.features.main.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kt.startkit.R
import com.kt.startkit.ui.features.main.LocalNavigationProvider
import com.kt.startkit.ui.features.main.root.NavigationRoute
import com.kt.startkit.ui.features.main.root.navigateToSettingItem


@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    viewModel: SettingScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        ProfileView(onItemClick = onItemClick)
        Spacer(modifier = Modifier.height(30.dp))
        AppSettingView()
        Spacer(modifier = Modifier.height(30.dp))
        CustomerView()
    }
}

//@Preview(showBackground = true)
@Composable
private fun ProfileView(
    onItemClick: (String) -> Unit,
) {
    val navController = LocalNavigationProvider.current

    Column() {
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp),
            text = stringResource(R.string.profile),
            fontWeight = FontWeight.Bold
        )
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()  //fill the max height
                .height(1.dp)
        )
        SettingItemView(
            title = "이름",
            value = "우당탕탕",
            onClick = {
                navController.navigateToSettingItem(NavigationRoute.SETTING_PROFILE_NAME.routeName)
            }
//            onClick = {
//                onItemClick(NavigationRoute.SETTING_PROFILE_NAME.routeName)
//            },
        )
        SettingItemView(
            title = "이메일",
            value = "알수없음",
            onClick = {},
        )
    }

}

@Composable
private fun AppSettingView() {
    Column(

    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp),
            text = "앱 설정",
            fontWeight = FontWeight.Bold
        )
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()  //fill the max height
                .height(1.dp)
        )
        SettingItemView(
            title = "테마",
            value = "라이트",
            onClick = {},
        )
        SettingItemView(
            title = "푸시",
            value = "허용",
            onClick = {},
        )
    }
}

@Composable
private fun CustomerView() {
    Column() {
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp),
            text = "고객센터",
            fontWeight = FontWeight.Bold
        )
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()  //fill the max height
                .height(1.dp)
        )
        SettingItemView(
            title = "공지사항",
            onClick = {},
        )
        SettingItemView(
            title = "FAQ",
            onClick = {},
        )
        SettingItemView(
            title = "문의하기",
            onClick = {},
        )
    }
}

@Composable
private fun SettingItemView(
    title: String,
    value: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .weight(1f)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
//        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = title,
        )
        Spacer(modifier = Modifier.width(16.dp))
        value?.let {
            Text(text = it)
        }

    }
}
