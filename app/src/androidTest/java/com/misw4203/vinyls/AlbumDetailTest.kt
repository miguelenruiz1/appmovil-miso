package com.misw4203.vinyls

import com.misw4203.vinyls.ui.album.AlbumDetail
import org.junit.Assert.assertEquals
import org.junit.Test

class AlbumDetailTest {

    @Test
    fun albumDetailShouldHaveCorrectProperties() {
        // Arrange: Crear un objeto AlbumDetail con valores de prueba
        val albumDetail = AlbumDetail(
            id = 100,
            title = "Buscando América",
            description = "Buscando América es el primer álbum de Rubén Blades",
            imageUrl = "https://example.com/album-cover.jpg",
            releaseDate = "1984-08-01",
            genre = "Salsa"
        )

        // Act & Assert: Verificar cada propiedad del objeto
        assertEquals(100, albumDetail.id)
        assertEquals("Buscando América", albumDetail.title)
        assertEquals("Buscando América es el primer álbum de Rubén Blades", albumDetail.description)
        assertEquals("https://example.com/album-cover.jpg", albumDetail.imageUrl)
        assertEquals("1984-08-01", albumDetail.releaseDate)
        assertEquals("Salsa", albumDetail.genre)
    }
}
