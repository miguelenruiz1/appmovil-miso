package com.misw4203.vinyls.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.misw4203.vinyls.databinding.ItemTrackBinding
import com.misw4203.vinyls.models.Track

class TracksAdapter : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    var tracks: List<Track> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int = tracks.size

    class TrackViewHolder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.trackName.text = track.name
            binding.trackDuration.text = track.duration
        }
    }
}
