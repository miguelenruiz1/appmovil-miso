package com.misw4203.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.models.Comment
import com.misw4203.vinyls.models.createAlbum
import com.misw4203.vinyls.network.NetworkServiceAdapter

class AlbumsRepository (val application: Application) {

    private val networkService = NetworkServiceAdapter.getInstance(application)

    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        networkService.getAlbums({
            callback(it)
        },
            onError
        )
    }

    fun createAlbum(
        album: createAlbum,
        onSuccess: (createAlbum) -> Unit,
        onError: (String) -> Unit
    ) {
        networkService.createAlbum(
            album = album,
            onComplete = { createdAlbum ->
                onSuccess(createdAlbum)
            },
            onError = { error ->
                val errorMessage = error.localizedMessage ?: "Error desconocido al crear el álbum"
                onError(errorMessage)
            }
        )
    }

    fun createComment(
        albumId: Int,
        collectorId: Int,
        comment: Comment,
        onSuccess: (Comment) -> Unit,
        onError: (String) -> Unit
    ) {
        networkService.createComment(
            albumId,
            collectorId,
            comment,
            onComplete = onSuccess,
            onError = { error ->
                val errorMessage = error.localizedMessage ?: "Error desconocido al crear el álbum"
                onError(errorMessage)
            }
        )
    }
}