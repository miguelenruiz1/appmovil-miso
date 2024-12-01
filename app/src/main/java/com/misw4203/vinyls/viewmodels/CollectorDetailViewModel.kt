package com.misw4203.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.misw4203.vinyls.database.VinylRoomDatabase
import com.misw4203.vinyls.models.CollectorDetail
import com.misw4203.vinyls.repositories.CollectorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectorDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val collectorsRepository = CollectorsRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).collectorsDao()
    )

    private val _collectorDetail = MutableLiveData<CollectorDetail>()

    val collectorDetail: LiveData<CollectorDetail>
        get() = _collectorDetail

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    fun retrieveCollectorDetail(collectorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectorsRepository.getCollectorDetail(
                collectorId,
                {
                    _collectorDetail.postValue(it)
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
            if (modelClass.isAssignableFrom(CollectorDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
