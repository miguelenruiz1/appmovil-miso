package com.misw4203.vinyls.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collector_albums_table")
data class CollectorAlbum (
    @PrimaryKey val id: Int,
    val collector: Collector?,
    val album: Album?,
    val status: String,
    val price: Float,
)