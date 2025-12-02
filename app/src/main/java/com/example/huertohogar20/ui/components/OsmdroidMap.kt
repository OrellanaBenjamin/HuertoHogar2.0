package com.example.huertohogar20.ui.components

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import com.example.huertohogar20.model.Sucursal

@Composable
fun OsmdroidSucursalMap(
    sucursales: List<Sucursal>,
    selectedSucursal: Sucursal? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }

    // Efecto para centrar el mapa cuando se selecciona una sucursal
    LaunchedEffect(selectedSucursal) {
        selectedSucursal?.let { sucursal ->
            mapView?.controller?.apply {
                animateTo(GeoPoint(sucursal.latitud, sucursal.longitud))
                setZoom(15.0)
            }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx: Context ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(6.0) // Zoom inicial más alejado para ver varias sucursales

                // Centrar en la primera sucursal o en Chile central
                val center = selectedSucursal ?: sucursales.firstOrNull()
                center?.let {
                    controller.setCenter(GeoPoint(it.latitud, it.longitud))
                }

                // Agrega un marcador por cada sucursal
                sucursales.forEach { sucursal ->
                    val marker = Marker(this)
                    marker.position = GeoPoint(sucursal.latitud, sucursal.longitud)
                    marker.title = sucursal.nombre
                    marker.subDescription = sucursal.direccion
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    overlays.add(marker)
                }

                mapView = this
            }
        },
        update = { view ->
            selectedSucursal?.let { sucursal ->
                view.controller.animateTo(GeoPoint(sucursal.latitud, sucursal.longitud))
                view.controller.setZoom(15.0)
            }
        }
    )
}

@Composable
fun OsmdroidMap(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = { ctx: Context ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(15.0)
                controller.setCenter(GeoPoint(latitude, longitude))
            }
        }
    )
}
