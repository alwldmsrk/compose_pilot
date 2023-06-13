package com.kt.startkit.ui.features.main.map

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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kt.startkit.domain.entity.Item

@Composable
fun MapScreen(
    viewModel: MapScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
//    val state by viewModel.state.collectAsState()

    when (state) {
        is MapViewState.Initial -> {
            viewModel.fetchInitialData()
        }
        is MapViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is MapViewState.Data -> {
//            HomeContentView((state as HomeViewState.Data).items)
            /** Add Google map Sample */
            val yangjae = LatLng(37.484557, 127.034022)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(yangjae, 13f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = false)
            ) {
                Marker(
                    state = MarkerState(position = yangjae),
                    title = "Yangjae",
                    snippet = "Marker in Yangjae"
                )
            }
        }

        is MapViewState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    (state as MapViewState.Error).message,
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


