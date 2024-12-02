package com.misw4203.vinyls

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.misw4203.vinyls.ui.album.AlbumsFragment
import org.junit.Test
import android.os.SystemClock
import androidx.test.espresso.matcher.ViewMatchers.withId

class AlbumUITest {

    @Test
    fun albumDetailsAreDisplayedCorrectly() {
        // Lanza el fragmento en un contenedor para pruebas con el tema específico
        launchFragmentInContainer<AlbumsFragment>(
            themeResId = R.style.Theme_Vinyls // Especifica el tema
        )

        // Espera un breve período para que el RecyclerView se cargue
        SystemClock.sleep(5000)

        // Verifica que el RecyclerView esté visible
        onView(withId(R.id.albumsRv))
            .check(matches(isDisplayed()))

        // Verificación de los elementos específicos en el RecyclerView
        onView(RecyclerViewMatcher.withRecyclerView(R.id.albumsRv).atPositionOnView(0, R.id.albumName))
            .check(matches(isDisplayed()))

        onView(RecyclerViewMatcher.withRecyclerView(R.id.albumsRv).atPositionOnView(0, R.id.albumGenre))
            .check(matches(isDisplayed()))

        onView(RecyclerViewMatcher.withRecyclerView(R.id.albumsRv).atPositionOnView(0, R.id.header_image))
            .check(matches(isDisplayed()))
    }
}
