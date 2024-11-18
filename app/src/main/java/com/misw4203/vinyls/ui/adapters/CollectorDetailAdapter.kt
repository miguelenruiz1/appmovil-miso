package com.misw4203.vinyls.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.misw4203.vinyls.R
import com.misw4203.vinyls.databinding.CollectorDetailAlbumItemBinding
import com.misw4203.vinyls.databinding.CollectorDetailCommentItemBinding
import com.misw4203.vinyls.databinding.CollectorItemBinding
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.models.Collector
import com.misw4203.vinyls.models.CollectorAlbum
import com.misw4203.vinyls.models.CollectorDetail
import com.misw4203.vinyls.models.Comment

class CollectorDetailAlbumsAdapter : RecyclerView.Adapter<CollectorDetailAlbumsAdapter.CollectorDetailAlbumViewHolder>(){

    var albums: List<CollectorAlbum> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CollectorDetailAlbumViewHolder(val viewDataBinding: CollectorDetailAlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_detail_album_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorDetailAlbumViewHolder {
        val withDataBinding: CollectorDetailAlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorDetailAlbumViewHolder.LAYOUT,
            parent,
            false)
        return CollectorDetailAlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorDetailAlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
        }
//        holder.viewDataBinding.root.setOnClickListener {
//            val action = CollectorFragmentDirections.actionCollectorFragmentToAlbumFragment()
//            // Navigate using that action
//            holder.viewDataBinding.root.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


}

class CollectorDetailCommentsAdapter : RecyclerView.Adapter<CollectorDetailCommentsAdapter.CollectorDetailCommentViewHolder>(){

    var comments: List<Comment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CollectorDetailCommentViewHolder(val viewDataBinding: CollectorDetailCommentItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_detail_comment_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorDetailCommentViewHolder {
        val withDataBinding: CollectorDetailCommentItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorDetailCommentViewHolder.LAYOUT,
            parent,
            false)
        return CollectorDetailCommentViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorDetailCommentViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.comment = comments[position]
        }
//        holder.viewDataBinding.root.setOnClickListener {
//            val action = CollectorFragmentDirections.actionCollectorFragmentToAlbumFragment()
//            // Navigate using that action
//            holder.viewDataBinding.root.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }


}