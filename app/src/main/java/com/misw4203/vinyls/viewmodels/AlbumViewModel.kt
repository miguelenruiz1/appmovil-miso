package com.misw4203.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.android.volley.VolleyError
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.network.NetworkServiceAdapter

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _albumDetail = MutableLiveData<Album>()
    val albumDetail: LiveData<Album>
        get() = _albumDetail

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        fetchAlbumsFromNetwork()
    }

    private fun fetchAlbumsFromNetwork() {
        NetworkServiceAdapter.getInstance(getApplication()).getAlbums(
            onComplete = {
                _albums.value = it
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            },
            onError = {
                _eventNetworkError.value = true
            }
        )
    }

    fun getAlbumDetails(albumId: Int) {
        NetworkServiceAdapter.getInstance(getApplication()).getAlbumDetails(
            albumId,
            onComplete = { album ->
                _albumDetail.value = album
                _eventNetworkError.value = false
            },
            onError = {
                _eventNetworkError.value = true
            }
        )
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}
