package com.misw4203.vinyls

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> = atPositionOnView(position, -1)

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("has view id $targetViewId at position $position")
            }

            public override fun matchesSafely(view: View): Boolean {
                val recyclerView = view.rootView.findViewById(recyclerViewId) as RecyclerView
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                    ?: return false
                return if (targetViewId == -1) {
                    view == viewHolder.itemView
                } else {
                    view == viewHolder.itemView.findViewById(targetViewId)
                }
            }
        }
    }

    companion object {
        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }
    }
}
