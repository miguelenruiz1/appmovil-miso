package com.misw4203.vinyls.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.misw4203.vinyls.databinding.AlbumItemBinding
import com.misw4203.vinyls.models.Album

class AlbumsAdapter(private val onAlbumClick: (Album) -> Unit) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    var albums: List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding, onAlbumClick)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int = albums.size

    class AlbumViewHolder(
        private val binding: AlbumItemBinding,
        private val onAlbumClick: (Album) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.album = album
            binding.executePendingBindings()

            // Maneja clics en el álbum
            binding.root.setOnClickListener {
                onAlbumClick(album)
            }
        }
    }
}
