package com.misw4203.vinyls

import android.app.Application
import com.misw4203.vinyls.database.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}