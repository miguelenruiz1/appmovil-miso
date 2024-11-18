package com.misw4203.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.misw4203.vinyls.models.PerformerDetail
import com.misw4203.vinyls.repositories.PerformersRepository

class PerformerDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val performersRepository = PerformersRepository(application)

    private val _performerDetail = MutableLiveData<PerformerDetail>()

    val performerDetail: LiveData<PerformerDetail>
        get() = _performerDetail

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    fun retrievePerformerDetail(performerId: Int) {
        performersRepository.getPerformerDetail(
            performerId,
            {
            _performerDetail.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
