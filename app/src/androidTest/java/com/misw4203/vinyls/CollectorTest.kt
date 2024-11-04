package com.misw4203.vinyls

import com.misw4203.vinyls.models.Collector
import org.junit.Assert.assertEquals
import org.junit.Test

class CollectorTest {
    @Test
    fun collectorShouldHaveCorrectProperties() {
        val collector = Collector(
            collectorId = 1,
            name = "Test Collector",
            telephone = "3173099698",
            email = "test@gmail.com" ,
            image = "image.jpg")

        assertEquals(1, collector.collectorId)
        assertEquals("Test Collector", collector.name)
        assertEquals("3173099698", collector.telephone)
        assertEquals("test@gmail.com", collector.email)
        assertEquals("image.jpg",collector.image)
    }
}
