package com.misw4203.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.network.NetworkServiceAdapter

class AlbumsRepository (val application: Application) {
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }
}