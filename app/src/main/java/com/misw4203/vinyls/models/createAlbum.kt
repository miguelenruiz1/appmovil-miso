package com.misw4203.vinyls.models

data class createAlbum(
    val name: String, // Nombre del álbum
    val cover: String, // URL de la portada del álbum
    val releaseDate: String, // Fecha de lanzamiento
    val description: String, // Descripción del álbum
    val genre: String, // Género musical
    val recordLabel: String // Sello discográfico
)
