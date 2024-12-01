package com.misw4203.vinyls.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments_table")
data class Comment (
    @PrimaryKey val id: Int? = null,
    val description: String,
    val rating: Int,
    @ColumnInfo(index = true) val albumId: Int? = null,
    val collectorId: Int? = null
)