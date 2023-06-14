package com.kt.startkit.ui.features.main.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kt.startkit.R
import com.kt.startkit.core.logger.Logger
import com.kt.startkit.core.permission.PermissionUtil
import com.kt.startkit.domain.entity.FavoriteData
import com.kt.startkit.domain.entity.PlaceData
import com.kt.startkit.ui.component.dialog.ShowDialog
import com.kt.startkit.ui.features.main.map.component.PlaceSearchTextField
import com.kt.startkit.ui.features.main.web.WebViewScreen

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
            Box(
                modifier = Modifier
            ) {
                GoogleMapScreen(data = state as MapViewState.Data)
                SearchTextField {
                    with(viewModel) {
                        sendUiAction(UiAction.SearchPlace)
                    }
                }
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


/**
 * 장소 검색을 위한 TextField
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchTextField(
    viewModel: MapScreenViewModel = hiltViewModel(),
    onClickSearch: () -> Unit
) {
    val textState = viewModel.searchText
    val keyboardController = LocalSoftwareKeyboardController.current
    PlaceSearchTextField(modifier = Modifier
        .padding(top = 20.dp)
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 10.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(colorResource(R.color.grey_50)),
        value = textState,
        onSearchKeyboardAction = {
            if (textState.isNotEmpty()) {
                onClickSearch()
                keyboardController?.hide()
            }
        },
        onValueChange = { viewModel.setSearchText(it) })
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun GoogleMapScreen(
    data: MapViewState.Data,
    viewModel: MapScreenViewModel = hiltViewModel()
) {

    val locationPermissionState = rememberMultiplePermissionsState(permissions = PermissionUtil.locationPermissions)
    val yangjae = LatLng(37.484557, 127.034022)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(yangjae, 13f)
    }
    val clickedMarkerState = remember { mutableStateOf<MarkerClickData?>(null) }
    val clickedMarker = clickedMarkerState.value
    if (clickedMarker != null) {
        MarkerClickProcess(markerClickData = clickedMarker, dismiss = { clickedMarkerState.value = null })
    }

    CameraPositionChangeListen(
        cameraPositionState = cameraPositionState,
        onChanged = { rect ->
            viewModel.sendUiAction(
                UiAction.CameraChange(
                    left = rect.southwest.longitude,
                    top = rect.northeast.latitude,
                    right = rect.northeast.longitude,
                    bottom = rect.southwest.latitude
                )
            )
        })

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = !isAllPermissionStateDenied(locationPermissionState.permissions)),
    ) {
        AddPlaceMarkers(placeDatas = data.placeItems, onClick = { clickedMarkerState.value = it })
        AddFavoriteMarkers(favoriteDatas = data.favorites, onClick = { clickedMarkerState.value = it })
    }
}

/**
 * 정확한 위치, 대략적 위치 하나만 있으면 현재 위치 허용
 */
@OptIn(ExperimentalPermissionsApi::class)
private fun isAllPermissionStateDenied(permissionStates: List<PermissionState>): Boolean {
    for (permissionState in permissionStates) {
        if (permissionState.status == PermissionStatus.Granted) {
            return false
        }
    }
    return true
}

/**
 * 유저 터치로 화면 이동시 카메라 변경 체크
 */
@Composable
fun CameraPositionChangeListen(cameraPositionState: CameraPositionState, onChanged: (LatLngBounds) -> Unit) {
    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.position }
            .collect {
                val rect = cameraPositionState.projection?.visibleRegion?.latLngBounds
                rect?.let {
                    onChanged(it)
                }
            }
    }
}

@Composable
fun AddPlaceMarkers(placeDatas: List<PlaceData>, onClick: (MarkerClickData) -> Unit) {
    for (placeData in placeDatas) {
        Logger.i("placeData => y : ${placeData.y} x : ${placeData.x}, name = ${placeData.place_name}")
        Marker(
            state = MarkerState(position = LatLng(placeData.y.toDouble(), placeData.x.toDouble())),
            title = placeData.place_name,
            snippet = placeData.address_name,
            tag = MarkerType.PLACE.tag,
            onInfoWindowClick = {
                onClick(MarkerClickData.Place(placeData, it))
            }
        )
    }
}

@Composable
fun AddFavoriteMarkers(favoriteDatas: List<FavoriteData>, onClick: (MarkerClickData) -> Unit) {
    for (favoriteData in favoriteDatas) {
        Logger.i("favoriteData => y : ${favoriteData.lat} x : ${favoriteData.lng}, name = ${favoriteData.name}, url = ${favoriteData.url}")
        Marker(
            state = MarkerState(position = LatLng(favoriteData.lat, favoriteData.lng)),
            title = favoriteData.name,
            snippet = favoriteData.address,
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
            tag = MarkerType.FAVORITE.tag,
            onInfoWindowClick = {
                onClick(MarkerClickData.Favorite(favoriteData, it))
            }
        )
    }
}

@Composable
fun MarkerClickProcess(
    markerClickData: MarkerClickData,
    dismiss: () -> Unit,
    viewModel: MapScreenViewModel = hiltViewModel()
) {
    when (markerClickData) {
        is MarkerClickData.Place -> {
            ShowDialog(
                onDismissRequest = {
                    dismiss()
                },
                onButtonClick = {
                    viewModel.sendUiAction(
                        UiAction.AddFavoritePlace(
                            lat = markerClickData.place.y.toDouble(),
                            lng = markerClickData.place.x.toDouble(),
                            name = markerClickData.place.place_name,
                            address = markerClickData.place.address_name,
                            url = markerClickData.place.place_url
                        )
                    )
                    markerClickData.marker.isVisible = false
                    dismiss()
                },
                contentText = "관심 장소에 추가하시겠습니까? "
            )
        }

        is MarkerClickData.Favorite -> {
            ShowDialog(
                onDismissRequest = {
                    dismiss()
                },
                onButtonClick = {
                    viewModel.sendUiAction(
                        UiAction.RemoveFavoritePlace(markerClickData.favorite.name)
                    )
                    dismiss()
                },
                contentText = "관심 장소에서 제거하시겠습니까?"
            )
        }

        else -> {}
    }
}

sealed class MarkerClickData {
    data class Place(val place: PlaceData, val marker: Marker): MarkerClickData()
    data class Favorite(val favorite: FavoriteData, val marker: Marker): MarkerClickData()
}

enum class MarkerType(val tag: String) {
    PLACE("place"), FAVORITE("favorite")
}

