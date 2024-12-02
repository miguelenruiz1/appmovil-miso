package com.misw4203.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.misw4203.vinyls.models.Performer

@Dao
interface PerformersDao {
    @Query("SELECT * FROM performers_table")
    fun getPerformers():List<Performer>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(performer: Performer)

    @Query("DELETE FROM performers_table")
    suspend fun deleteAll()
}