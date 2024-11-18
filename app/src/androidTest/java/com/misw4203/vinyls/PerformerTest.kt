package com.misw4203.vinyls


import com.misw4203.vinyls.models.Performer
import org.junit.Assert.assertEquals
import org.junit.Test

class PerformerTest {

    @Test
    fun performerShouldHaveCorrectProperties() {
        val performer = Performer(
            id = 1,
            name = "Test Performer",
            description = "This is a test performer",
            birthday = "11/04/1997" ,
            image = "image.jpg")

        assertEquals(1, performer.id)
        assertEquals("Test Performer", performer.name)
        assertEquals("This is a test performer", performer.description)
        assertEquals("11/04/1997", performer.birthday)
        assertEquals("image.jpg",performer.image)
}
}

