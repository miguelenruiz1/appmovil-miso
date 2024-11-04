package com.misw4203.vinyls.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.misw4203.vinyls.models.Performer
import com.misw4203.vinyls.network.NetworkServiceAdapter

class PerformersRepository (val application: Application) {
    fun refreshData(callback: (List<Performer>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getPerformers({
            callback(it)
        },
            onError
        )
    }
}