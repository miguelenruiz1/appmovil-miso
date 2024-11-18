package com.misw4203.vinyls

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import android.os.Bundle
import android.os.SystemClock
import com.misw4203.vinyls.ui.album.AlbumDetailFragment
import org.junit.Test
import org.hamcrest.CoreMatchers.containsString


class AlbumDetailFragmentTestUI {

    @Test
    fun albumDetailsAreDisplayedCorrectly() {
        // Crear un Bundle con el argumento necesario
        val fragmentArgs = Bundle().apply {
            putInt("albumId", 100) //
        }

        // Lanza el fragmento con los argumentos necesarios
        launchFragmentInContainer<AlbumDetailFragment>(
            fragmentArgs = fragmentArgs,
            themeResId = R.style.Theme_Vinyls
        )
        SystemClock.sleep(10000)
        // Verifica que el título del álbum es visible
        onView(withId(R.id.albumTitle))
            .check(matches(isDisplayed()))

        // Verifica el contenido del título
        onView(withId(R.id.albumTitle))
            .check(matches(withText(containsString("Buscando América"))))
    }
}
