package com.misw4203.vinyls.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.android.volley.VolleyError
import com.misw4203.vinyls.database.AlbumsDao
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.models.Comment
import com.misw4203.vinyls.models.createAlbum
import com.misw4203.vinyls.network.NetworkServiceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumsRepository (val application: Application, private val albumsDao: AlbumsDao) {

    private val networkService = NetworkServiceAdapter.getInstance(application)

    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        var cached = albumsDao.getAlbums()
        if(cached.isEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                callback(emptyList())
            } else {
                networkService.getAlbums(
                    { albums ->
                        // Wrap the insert operation inside a coroutine
                        CoroutineScope(Dispatchers.IO).launch {
                            albums.forEach {
                                albumsDao.insert(it)  // Call suspend function inside a coroutine
                            }
                            withContext(Dispatchers.Main) {
                                callback(albums)  // Call callback on the main thread
                            }
                        }
                    },
                    onError
                )
            }
        } else {
            callback(cached)
        }
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