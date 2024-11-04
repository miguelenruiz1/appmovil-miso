package com.misw4203.vinyls

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.misw4203.vinyls.ui.collector.CollectorFragment
import org.junit.Test
import android.os.SystemClock
import androidx.test.espresso.matcher.ViewMatchers.withId

class CollectorUITest {

    @Test
    fun collectorDetailsAreDisplayedCorrectly() {
        // Lanza el fragmento en un contenedor para pruebas con el tema específico
        launchFragmentInContainer<CollectorFragment>(
            themeResId = R.style.Theme_Vinyls // Especifica el tema
        )

        // Espera un breve período para que el RecyclerView se cargue
        SystemClock.sleep(4000)

        // Verifica que el RecyclerView esté visible
        onView(withId(R.id.collectorsRv))
            .check(matches(isDisplayed()))

        // Verificación de los elementos específicos en el RecyclerView
        onView(RecyclerViewMatcher.withRecyclerView(R.id.collectorsRv).atPositionOnView(0, R.id.collectorName))
            .check(matches(withText("Manolo Bellon")))

        onView(RecyclerViewMatcher.withRecyclerView(R.id.collectorsRv).atPositionOnView(0, R.id.collectorAlbums))
            .check(matches(withText("25 álbumes")))

        onView(RecyclerViewMatcher.withRecyclerView(R.id.collectorsRv).atPositionOnView(0, R.id.collectorImage))
            .check(matches(isDisplayed()))
    }
}