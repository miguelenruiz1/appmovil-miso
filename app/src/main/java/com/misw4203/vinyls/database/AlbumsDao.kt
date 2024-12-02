package com.misw4203.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.misw4203.vinyls.models.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table")
    fun getAlbums():List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll()
}