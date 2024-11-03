// BindingAdapters.kt
package com.misw4203.vinyls.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.github.ivbaranov.mli.MaterialLetterIcon


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context)
                .load(url)
                .signature(ObjectKey((0..1000000).random()))
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("mli_letter")
    fun setMaterialLetterIconLetter(view: MaterialLetterIcon, letter: String?) {
        letter?.let {
            view.letter = it
        }
    }
}
