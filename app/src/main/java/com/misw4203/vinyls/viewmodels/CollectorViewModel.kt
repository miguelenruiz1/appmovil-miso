package com.misw4203.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.misw4203.vinyls.database.VinylRoomDatabase
import com.misw4203.vinyls.models.Collector
import com.misw4203.vinyls.models.CollectorDetail
import com.misw4203.vinyls.network.NetworkServiceAdapter
import com.misw4203.vinyls.repositories.CollectorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectorViewModel(application: Application) : AndroidViewModel(application) {

    private val collectorsRepository = CollectorsRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).collectorsDao()
    )

    private val _collectors = MutableLiveData<List<Collector>>()

    val collectors: LiveData<List<Collector>>
        get() = _collectors

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
            collectorsRepository.refreshData({
                _collectors.postValue(it)
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
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
