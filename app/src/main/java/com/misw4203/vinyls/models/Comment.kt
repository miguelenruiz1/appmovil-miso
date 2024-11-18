package com.misw4203.vinyls.models

data class Comment (
    val id: Int,
    val description: String,
    val rating: Float,
    val album: Album?,
    val collector: Collector?
)