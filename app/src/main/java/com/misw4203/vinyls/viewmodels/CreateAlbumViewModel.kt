package com.misw4203.vinyls.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw4203.vinyls.models.createAlbum
import com.misw4203.vinyls.repositories.CreateAlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAlbumViewModel(private val repository: CreateAlbumRepository) : ViewModel() {

    private val _albumCreated = MutableLiveData<createAlbum?>()
    val albumCreated: LiveData<createAlbum?> get() = _albumCreated

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Crear un álbum
    fun createAlbum(album: createAlbum) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createAlbum(
                album = album,
                onSuccess = { createdAlbum ->
                    _albumCreated.postValue(createdAlbum)
                    _error.postValue(null) // Resetea el error si el álbum se crea correctamente
                },
                onError = { errorMessage ->
                    _error.postValue(errorMessage)
                    _albumCreated.postValue(null) // Resetea el álbum creado si ocurre un error
                }
            )
        }
    }

    // Resetea los estados para usarlos nuevamente
    fun resetState() {
        _albumCreated.value = null
        _error.value = null
    }
}
