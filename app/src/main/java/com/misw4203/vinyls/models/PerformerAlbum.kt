package com.misw4203.vinyls.models

enum class AlbumStatus(status: String) {
    ACTIVE("Active"),
    INACTIVE("Inactive")
}

data class PerformerAlbum (
    val id: Int,
    val performer: Performer?,
    val album: Album?,
    val status: AlbumStatus,
)