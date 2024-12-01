// BindingAdapters.kt
package com.misw4203.vinyls.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.github.ivbaranov.mli.MaterialLetterIcon
import com.misw4203.vinyls.R


object BindingAdapters {

    var options: RequestOptions = RequestOptions()
        .dontAnimate()
        .centerCrop()
        .placeholder(R.drawable.ic_no_image) //Use a placeholder image
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .priority(Priority.HIGH)

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            if (url.contains("thispersondoesnotexist")) {
                Glide.with(view.context)
                    .load(url)
                    .signature(ObjectKey((0..1000000).random()))
                    .error(R.drawable.ic_no_image)
                    .apply(options)
                    .into(view)
            } else {
                Glide.with(view.context)
                    .load(url)
                    .error(R.drawable.ic_no_image)
                    .apply(options)
                    .into(view)
            }
        } else {
            view.setImageResource(R.drawable.ic_no_image)
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
