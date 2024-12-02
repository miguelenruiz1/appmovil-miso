package com.misw4203.vinyls.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.android.volley.VolleyError
import com.misw4203.vinyls.database.CollectorsDao
import com.misw4203.vinyls.models.Collector
import com.misw4203.vinyls.models.CollectorDetail
import com.misw4203.vinyls.network.NetworkServiceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.typeOf

class CollectorsRepository (val application: Application, private val collectorsDao: CollectorsDao) {

    private val networkAdapter = NetworkServiceAdapter.getInstance(application)

    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        var cached = collectorsDao.getCollectors()
        if(cached.isEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                callback(emptyList())
            } else {
                networkAdapter.getCollectors(
                    { collectors ->
                        // Wrap the insert operation inside a coroutine
                        CoroutineScope(Dispatchers.IO).launch {
                            collectors.forEach {
                                collectorsDao.insert(it)
                            }
                            withContext(Dispatchers.Main) {
                                callback(collectors)
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

    fun getCollectorDetail(collectorId: Int, callback: (CollectorDetail)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectorDetail(collectorId, {
            callback(it)
        },
            onError
        )
    }
}