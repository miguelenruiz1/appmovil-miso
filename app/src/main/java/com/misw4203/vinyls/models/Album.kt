package com.misw4203.vinyls.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "albums_table")
open class Album(
    @PrimaryKey val id: Int? = 0,
    val name: String? = "Default name",
    val cover: String? = "cover",
    val releaseDate: String? = "10/10/2010",
    val description: String? = "",
    val genre: String? = "",
    val recordLabel: String? = "",
)

class AlbumDetail(
    id: Int = 0,
    name: String? = "Default name",
    cover: String? = "cover",
    releaseDate: String? = "10/10/2010",
    description: String? = "",
    genre: String? = "",
    recordLabel: String? = "",
    @Relation(parentColumn = "id", entityColumn = "albumId")
    val tracks: List<Track> = emptyList(),
    @Relation(parentColumn = "id", entityColumn = "albumId")
    val performers: List<Performer> = emptyList(),
    @Relation(parentColumn = "id", entityColumn = "albumId")
    val comments: List<Comment> = emptyList()
) : Album(
    id, name, cover, releaseDate, description, genre, recordLabel
)

@Entity(tableName = "tracks_table",  foreignKeys = [
    ForeignKey(
        entity = Album::class,
        parentColumns = ["id"],
        childColumns = ["albumId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Track(
    @PrimaryKey val id: Int,
    val name: String,
    val duration: String,
    @ColumnInfo(index = true) val albumId: Int? = null
)
