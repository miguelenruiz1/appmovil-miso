// BindingAdapters.kt
package com.misw4203.vinyls.bindings

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.github.ivbaranov.mli.MaterialLetterIcon
import com.misw4203.vinyls.R


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url)
                .signature(ObjectKey((0..1000000).random()))
                .error(R.drawable.ic_phone)
                .into(view)
        } else {
            view.setImageResource(R.drawable.ic_phone)
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
