package com.misw4203.vinyls.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.misw4203.vinyls.database.VinylRoomDatabase
import com.misw4203.vinyls.models.Comment
import com.misw4203.vinyls.models.createAlbum
import com.misw4203.vinyls.repositories.AlbumsRepository
import com.misw4203.vinyls.repositories.CollectorsRepository
import com.misw4203.vinyls.viewmodels.AlbumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCommentViewModel(private val app: Application) : AndroidViewModel(app) {

    private val repository = AlbumsRepository(
        app,
        VinylRoomDatabase.getDatabase(app.applicationContext).albumsDao()
    )

    private val _commentCreated = MutableLiveData<Comment?>()
    val commentCreated: LiveData<Comment?> get() = _commentCreated

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Crear un comentario
    fun createComment(albumId: Int, collectorId: Int, description: String, rating: Int) {
        Log.d("CreateCommentViewModel", "Creating comment for album $albumId")
        viewModelScope.launch(Dispatchers.IO) {
            val comment = Comment(
                description = description,
                rating = rating
            )
            repository.createComment(albumId, collectorId, comment,
                { createdComment ->
                    _commentCreated.postValue(createdComment)
                    Toast.makeText(app, "Comentario creado exitosamente!", Toast.LENGTH_LONG).show()
                }, { errorMessage ->
                    _error.postValue(errorMessage)
                    Log.e("CreateCommentViewModel", "Error creating comment: $errorMessage")
                    Toast.makeText(
                        app,
                        "Error al crear el comentario: $errorMessage",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }

    // Resetea los estados para usarlos nuevamente
    fun resetState() {
        _commentCreated.value = null
        _error.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateCommentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateCommentViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}
