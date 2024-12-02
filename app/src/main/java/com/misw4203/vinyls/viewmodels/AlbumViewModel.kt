package com.misw4203.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.volley.VolleyError
import com.misw4203.vinyls.database.VinylRoomDatabase
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.models.AlbumDetail
import com.misw4203.vinyls.network.NetworkServiceAdapter
import com.misw4203.vinyls.repositories.AlbumsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AlbumsRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao()
    )

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _albumDetail = MutableLiveData<AlbumDetail>()
    val albumDetail: LiveData<AlbumDetail>
        get() = _albumDetail

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.refreshData({
                _albums.postValue(it)
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }, {
                _eventNetworkError.postValue(true)
            })
        }
    }

    fun getAlbumDetails(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            NetworkServiceAdapter.getInstance(getApplication()).getAlbumDetails(
                albumId,
                onComplete = { album ->
                    _albumDetail.postValue(album)
                    _eventNetworkError.postValue(false)
                },
                onError = {
                    _eventNetworkError.postValue(true)
                }
            )
        }
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
