package com.misw4203.vinyls

import com.misw4203.vinyls.models.CollectorDetail
import com.misw4203.vinyls.models.Comment
import com.misw4203.vinyls.ui.album.AlbumDetail
import org.junit.Assert.assertEquals
import org.junit.Test

class CollectorDetailTest {

    @Test
    fun collectorDetailShouldHaveCorrectProperties() {
        // Arrange: Crear un objeto CollectorDetail con valores de prueba
        val collectorDetail = CollectorDetail(
            id = 100,
            name = "Pepito Perez",
            email = "pepito@gmail.com",
            telephone = "3001234567",
            image = "http://www.google.com",
            comments = listOf(
                Comment(
                    id = 1,
                    rating = 5,
                    description = "Prueba de comentario"
                ),
                Comment(
                    id = 3,
                    rating = 3,
                    description = "Prueba de comentario 2"
                ),
            )
        )

        // Assert: Verificar que las propiedades del objeto CollectorDetail son correctas
        assertEquals(100, collectorDetail.id)
        assertEquals("Pepito Perez", collectorDetail.name)
        assertEquals("pepito@gmail.com", collectorDetail.email)
        assertEquals("3001234567", collectorDetail.telephone)
        assertEquals("http://www.google.com", collectorDetail.image)
        assertEquals(2, collectorDetail.comments.size)
        assertEquals(1, collectorDetail.comments[0].id)
        assertEquals(5, collectorDetail.comments[0].rating)
        assertEquals("Prueba de comentario", collectorDetail.comments[0].description)
        assertEquals(3, collectorDetail.comments[1].id)
        assertEquals(3, collectorDetail.comments[1].rating)
        assertEquals("Prueba de comentario 2", collectorDetail.comments[1].description)
    }

    @Test
    fun collectorDetailShouldHandleEmptyComments() {
        // Arrange: Crear un objeto CollectorDetail con una lista de comentarios vacía
        val collectorDetail = CollectorDetail(
            id = 101,
            name = "Juan Lopez",
            email = "juanlopez@gmail.com",
            telephone = "3101234567",
            image = "http://www.example.com",
            comments = listOf()
        )

        // Assert: Verificar que las propiedades del objeto CollectorDetail son correctas
        assertEquals(101, collectorDetail.id)
        assertEquals("Juan Lopez", collectorDetail.name)
        assertEquals("juanlopez@gmail.com", collectorDetail.email)
        assertEquals("3101234567", collectorDetail.telephone)
        assertEquals("http://www.example.com", collectorDetail.image)
        assertEquals(0, collectorDetail.comments.size)
    }
}
