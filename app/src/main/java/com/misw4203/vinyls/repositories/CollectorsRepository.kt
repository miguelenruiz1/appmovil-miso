package com.misw4203.vinyls.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.misw4203.vinyls.models.Collector
import com.misw4203.vinyls.models.CollectorDetail
import com.misw4203.vinyls.network.NetworkServiceAdapter
import kotlin.reflect.typeOf

class CollectorsRepository (val application: Application) {
    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectors({
            callback(it)
        },
            onError
        )
    }

    fun getCollectorDetail(collectorId: Int, callback: (CollectorDetail)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectorDetail(collectorId, {
            callback(it)
        },
            onError
        )
    }
}