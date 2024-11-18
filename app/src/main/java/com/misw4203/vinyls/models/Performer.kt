package com.misw4203.vinyls.models

data class Performer (
    val id:Int,
    val name:String,
    val image:String,
    val birthday:String,
    val description:String
)

class PerformerDetail(
    id: Int = 0,
    name: String = "Default Name",
    image: String = "https://thispersondoesnotexist.com/",
    birthday: String = "test",
    description: String = "description",
    val performerAlbums: List<PerformerAlbum> = emptyList()
) : Performer(
    id, name, image, birthday, description
) {
    override fun toString(): String {
        return "PerformerDetail: $id $name $image"
    }
}