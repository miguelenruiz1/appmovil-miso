package com.misw4203.vinyls.models

enum class AlbumStatus(status: String) {
    ACTIVE("Active"),
    INACTIVE("Inactive")
}

data class CollectorAlbum (
    val id: Int,
    val collector: Collector?,
    val album: Album?,
    val status: String,
    val price: Float,
)