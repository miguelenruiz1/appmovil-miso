package com.misw4203.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.misw4203.vinyls.database.VinylRoomDatabase
import com.misw4203.vinyls.models.Performer
import com.misw4203.vinyls.network.NetworkServiceAdapter
import com.misw4203.vinyls.repositories.CollectorsRepository
import com.misw4203.vinyls.repositories.PerformersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerformerViewModel(application: Application) : AndroidViewModel(application) {
    private val _performers = MutableLiveData<List<Performer>>()

    private val performersRepository = PerformersRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).performersDao()
    )

    val performers: LiveData<List<Performer>>
        get() = _performers

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            performersRepository.refreshData({
                _performers.postValue(it)
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }, {
                _eventNetworkError.postValue(true)
            })
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.postValue(true)
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}