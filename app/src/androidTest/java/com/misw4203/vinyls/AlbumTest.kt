package com.misw4203.vinyls

import com.misw4203.vinyls.models.Album
import org.junit.Assert.assertEquals
import org.junit.Test

class AlbumTest {

    @Test
    fun albumShouldHaveCorrectProperties() {
        val album = Album(
            id = 1,
            name = "Test Album",
            cover = "http://example.com/cover.jpg",
            releaseDate = "2023-01-01",
            description = "This is a test album",
            genre = "Rock",
            recordLabel = "Test Label"
        )

        assertEquals(1, album.id)
        assertEquals("Test Album", album.name)
        assertEquals("http://example.com/cover.jpg", album.cover)
        assertEquals("2023-01-01", album.releaseDate)
        assertEquals("This is a test album", album.description)
        assertEquals("Rock", album.genre)
        assertEquals("Test Label", album.recordLabel)
    }
}
