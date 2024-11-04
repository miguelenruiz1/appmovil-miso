package com.misw4203.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.misw4203.vinyls.models.Collector
import com.misw4203.vinyls.network.NetworkServiceAdapter

class CollectorsRepository (val application: Application) {
    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectors({
            callback(it)
        },
            onError
        )
    }
}