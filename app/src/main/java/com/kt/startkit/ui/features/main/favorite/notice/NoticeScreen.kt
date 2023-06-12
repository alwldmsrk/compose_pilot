package com.kt.startkit.ui.features.main.setting.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kt.startkit.ui.res.IconRes
import com.kt.startkit.ui.res.IconResId
import com.kt.startkit.ui.features.main.setting.SettingScreenViewModel

@Composable
fun NoticeScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
//    viewModel: NoticeScreenViewModel = hiltViewModel(),
    viewModel: SettingScreenViewModel = hiltViewModel(),
) {
    Column {
        NoticeAppBar(onBackClick = onBackClick)
        NoticeContentView()
    }
}

@Composable
fun NoticeAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Row(
//        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = IconResId.ArrowBack,
                contentDescription = null,
            )
        }
        Text("공지사항")
//        val selected = uiState.isFollowed
//        NiaFilterChip(
//            selected = selected,
//            onSelectedChange = onFollowClick,
//            modifier = Modifier.padding(end = 24.dp),
//        ) {
//            if (selected) {
//                Text("FOLLOWING")
//            } else {
//                Text("NOT FOLLOWING")
//            }
//        }
    }
}

@Composable
private fun NoticeContentView() {

}