package com.misw4203.vinyls

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.misw4203.vinyls.ui.performer.PerformersFragment
import org.junit.Test
import android.os.SystemClock
import androidx.test.espresso.matcher.ViewMatchers.withId

class PerformerUITest {

    @Test
    fun performerDetailsAreDisplayedCorrectly() {
        // Lanza el fragmento en un contenedor para pruebas con el tema específico
        launchFragmentInContainer<PerformersFragment>(
            themeResId = R.style.Theme_Vinyls // Especifica el tema
        )

        // Espera un breve período para que el RecyclerView se cargue
        SystemClock.sleep(4000)

        // Verifica que el RecyclerView esté visible
        onView(withId(R.id.performers_rv))
            .check(matches(isDisplayed()))

        // Verificación de los elementos específicos en el RecyclerView
        onView(RecyclerViewMatcher.withRecyclerView(R.id.performers_rv).atPositionOnView(0, R.id.performers_rv))
            .check(matches(withText("Rubén Blades Bellido de Luna")))

        onView(RecyclerViewMatcher.withRecyclerView(R.id.performers_rv).atPositionOnView(0, R.id.performers_rv))
            .check(matches(withText("Artistas")))

        onView(RecyclerViewMatcher.withRecyclerView(R.id.performers_rv).atPositionOnView(0, R.id.performers_rv))
            .check(matches(isDisplayed()))
    }
}