package com.misw4203.vinyls.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.android.volley.VolleyError
import com.misw4203.vinyls.database.PerformersDao
import com.misw4203.vinyls.models.Performer
import com.misw4203.vinyls.network.NetworkServiceAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformersRepository (val application: Application, private val performersDao: PerformersDao) {

    private val networkAdapter: NetworkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    fun refreshData(callback: (List<Performer>)->Unit, onError: (VolleyError)->Unit) {
        val cached = performersDao.getPerformers()
        if(cached.isEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                callback(emptyList())
            } else {
                networkAdapter.getPerformers(
                    { performers ->
                        CoroutineScope(Dispatchers.IO).launch {
                            performers.forEach {
                                performersDao.insert(it)
                            }
                            withContext(Dispatchers.Main) {
                                callback(performers)
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
}