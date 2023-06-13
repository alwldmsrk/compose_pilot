package com.kt.startkit.ui.features.main.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

import com.kt.startkit.R
import com.kt.startkit.ui.features.main.favorite.component.ImageColumnContent
import com.kt.startkit.ui.features.main.favorite.component.ResultDataItem


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
            viewModel.getFavoriteList()
        }

        is FavoriteViewState.Error -> {
            EmptyFavoriteList()
        }

        is FavoriteViewState.Data -> {
            FavoriteList(state as FavoriteViewState.Data)
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
 * 즐겨찾기 리스트 표출 Composable
 */
@Composable
fun FavoriteList(
    listData: FavoriteViewState.Data,
    viewModel: FavoriteScreenViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(listData.favoriteItem) {
            ResultDataItem(
                bookMarkIcon = R.drawable.outline_favorite_black_24,
                onClickBookmark = {
                        viewModel.sendUiAction(FavoriteScreenViewModel.UiAction.DeleteBookMark)
                },
                columnContent = {
                    ImageColumnContent(
                        title = it.name,
                        address = it.address
                    )
                }
            )
        }
    }
}
