package com.misw4203.vinyls.models

import com.google.gson.annotations.SerializedName

interface CollectorInterface

open class Collector(
    val id: Int = 0,
    val name: String = "Default Name",
    val image: String = "https://thispersondoesnotexist.com/",
    val telephone: String = "1234566789",
    val email: String = "default@mail.com"
)

class CollectorDetail(
    id: Int = 0,
    name: String = "Default Name",
    image: String = "https://thispersondoesnotexist.com/",
    telephone: String = "123456789",
    email: String = "default@mail.com",
    val comments: List<Comment> = emptyList(),
    val favoritePerformers: List<Performer> = emptyList(),
    val collectorAlbums: List<CollectorAlbum> = emptyList()
) : Collector(
    id, name, image, telephone, email
) {
    override fun toString(): String {
        return "CollectorDetail: $id $name $image"
    }
}