package com.kt.startkit.ui.features.main.map

import android.app.Activity
import android.location.Location
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.core.permission.PermissionUtil
import com.kt.startkit.domain.entity.Item

@Composable
fun MapScreen(
    viewModel: MapScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

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
            GoogleMapScreen(data = state as MapViewState.Data)
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun GoogleMapScreen(data: MapViewState.Data) {

    val locationPermissionState = rememberMultiplePermissionsState(permissions = PermissionUtil.locationPermissions)
    val yangjae = LatLng(37.484557, 127.034022)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(yangjae, 13f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = !isAllPermissionStateDenied(locationPermissionState.permissions)),

    ) {
        Marker(
            state = MarkerState(position = yangjae),
            title = "Yangjae",
            snippet = "Marker in Yangjae"
        )
    }
}

/**
 * 정확한 위치, 대략적 위치 하나만 있으면 현재 위치 허용
 */
@OptIn(ExperimentalPermissionsApi::class)
private fun isAllPermissionStateDenied(permissionStates: List<PermissionState>): Boolean {
    for(permissionState in permissionStates) {
        if(permissionState.status == PermissionStatus.Granted) {
            return false
        }
    }
    return true
}

