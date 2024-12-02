package com.misw4203.vinyls.models

data class Comment (
    val id: Int? = null,
    val description: String,
    val rating: Int,
    val album: Album? = null,
    val collector: Collector? = null
)