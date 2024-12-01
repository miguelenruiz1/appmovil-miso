package com.misw4203.vinyls.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.models.Collector
import com.misw4203.vinyls.models.CollectorAlbum
import com.misw4203.vinyls.models.CollectorDetail
import com.misw4203.vinyls.models.Comment
import com.misw4203.vinyls.models.Performer
import com.misw4203.vinyls.models.Track

@Database(
    entities = [
        Album::class,
        Collector::class,
        Comment::class,
        Performer::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VinylRoomDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao
    abstract fun collectorsDao(): CollectorsDao
    abstract fun commentsDao(): CommentsDao
    abstract fun performersDao(): PerformersDao

    companion object {
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}