package com.misw4203.vinyls

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import android.os.Bundle
import android.os.SystemClock
import com.misw4203.vinyls.ui.album.AlbumDetailFragment
import com.misw4203.vinyls.ui.collector.CollectorDetailFragment
import org.junit.Test
import org.hamcrest.CoreMatchers.containsString


class CollectorDetailFragmentTestUI {

    @Test
    fun collectorDetailsAreDisplayedCorrectly() {
        // Crear un Bundle con el argumento necesario
        val fragmentArgs = Bundle().apply {
            putInt("collectorId", 100) //
        }

        // Lanza el fragmento con los argumentos necesarios
        launchFragmentInContainer<CollectorDetailFragment>(
            fragmentArgs = fragmentArgs,
            themeResId = R.style.Theme_Vinyls
        )
        SystemClock.sleep(10000)
        // Verifica que el nombre del coleccionista es visible y el contenido
        onView(withId(R.id.collectorDetailName))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Manolo Bellon"))))

        // Verifica que el email del coleccionista es visible
        onView(withId(R.id.collectorEmail))
            .check(matches(isDisplayed()))

        // Verifica el contenido del email
        onView(withId(R.id.collectorEmail))
            .check(matches(withText(containsString("manollo@caracol.com.co"))))

        // Verifica que el teléfono del coleccionista es visible
        onView(withId(R.id.collectorPhone))
            .check(matches(isDisplayed()))

        // Verifica el contenido del teléfono
        onView(withId(R.id.collectorPhone))
            .check(matches(withText(containsString("3502457896"))))
    }
}
