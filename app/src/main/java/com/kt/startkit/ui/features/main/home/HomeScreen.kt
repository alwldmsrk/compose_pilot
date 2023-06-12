package com.kt.startkit.ui.features.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.kt.startkit.domain.entity.Item

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
//    val state by viewModel.state.collectAsState()

    when (state) {
        is HomeViewState.Initial -> {
            viewModel.fetchInitialData()
        }
        is HomeViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is HomeViewState.Data -> {
            HomeContentView((state as HomeViewState.Data).items)
        }

        is HomeViewState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    (state as HomeViewState.Error).message,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}

@Composable
private fun HomeContentView(
    items: List<Item>,
) {
    LocalViewModelStoreOwner.current
    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        items(
            items = items,
            itemContent = { item ->
                HomeItemView(item = item)
            }
        )
    }
}

@Composable
private fun HomeItemView(item: Item) {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = item.name,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.price.toString() + "원")
    }
}


