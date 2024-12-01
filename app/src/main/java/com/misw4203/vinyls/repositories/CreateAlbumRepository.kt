package com.misw4203.vinyls.repositories

import android.content.Context
import com.misw4203.vinyls.models.createAlbum
import com.misw4203.vinyls.network.NetworkServiceAdapter

class CreateAlbumRepository(private val context: Context) {

    private val networkService = NetworkServiceAdapter.getInstance(context)

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
}
