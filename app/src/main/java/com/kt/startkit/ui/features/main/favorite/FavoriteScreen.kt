package com.kt.startkit.ui.features.main.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.kt.startkit.R
import com.kt.startkit.domain.entity.FavoriteData
import com.kt.startkit.ui.features.main.LocalNavigationProvider
import com.kt.startkit.ui.features.main.favorite.component.PlaceColumnContent
import com.kt.startkit.ui.features.main.favorite.component.ResultDataItem
import com.kt.startkit.ui.features.main.root.NavigationRoute


@Composable
fun FavoriteScreen(
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    when (state) {
        is FavoriteViewState.Loading -> {
            LoadingComponent()
        }

        is FavoriteViewState.Initial -> {
            viewModel.sendUiAction(FavoriteScreenViewModel.UiAction.GetBookmark)
        }

        is FavoriteViewState.Error -> {
            EmptyFavoriteList()
        }

        is FavoriteViewState.Data -> {
            FavoriteListWithPaging((state as FavoriteViewState.Data).favoriteItem.collectAsLazyPagingItems())
        }

        else -> {}
    }
}

@Composable
fun LoadingComponent() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

/**
 * empty 화면 표출용 Composable
 */
@Composable
fun EmptyFavoriteList() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.favorite_empty_message),
        )
    }
}

/**
 * 즐겨찾기 리스트 표출 Composable (with Paging)
 */
@Composable
fun FavoriteListWithPaging(
    listData: LazyPagingItems<FavoriteData>,
    viewModel: FavoriteScreenViewModel = hiltViewModel()
) {
    val navController = LocalNavigationProvider.current

    LazyColumn(
        modifier = Modifier
            .padding(vertical = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(listData) { item ->
            if(item != null){
                ResultDataItem(
                    //TODO onClickItem에 url 값 넣어주기
                    onClickItem = {
                        val url = item.url
                        navController.navigate("/detail?url=$url")
                    },
                    bookMarkIcon = R.drawable.outline_favorite_black_24,
                    onClickBookmark = {
                        viewModel.sendUiAction(FavoriteScreenViewModel.UiAction.DeleteBookmark(item.name))
                    },
                    columnContent = {
                        PlaceColumnContent(
                            title = item.name,
                            address = item.address
                        )
                    }
                )
            }
        }
    }

}


