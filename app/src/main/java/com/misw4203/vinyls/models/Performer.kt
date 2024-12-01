package com.misw4203.vinyls.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "performers_table",  foreignKeys = [
    ForeignKey(
        entity = Album::class,
        parentColumns = ["id"],
        childColumns = ["albumId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Performer (
    @PrimaryKey val id:Int,
    val name:String,
    val image:String,
    val birthday:String,
    val description:String,
    @ColumnInfo(index = true) val albumId: Int? = null
)