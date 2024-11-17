package com.misw4203.vinyls.models

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<Track> = emptyList(),
    val performers: List<PerformerDetails> = emptyList(),
    val comments: List<Comment> = emptyList()
)

data class Track(
    val id: Int,
    val name: String,
    val duration: String
)

data class Comment(
    val id: Int,
    val description: String,
    val rating: Int
)

data class PerformerDetails(
    val performerId: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthday: String
)
