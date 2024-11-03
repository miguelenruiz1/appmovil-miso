package com.misw4203.vinyls.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.misw4203.vinyls.R
import com.misw4203.vinyls.models.Performer


class PerformersAdapter : RecyclerView.Adapter<PerformersAdapter.PerformerViewHolder>() {

    var performers: List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val binding = PerformerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PerformerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        holder.bind(performers[position])
    }

    override fun getItemCount(): Int = performers.size

    class PerformerViewHolder(private val binding: PerformerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(performer: Performer) {
            binding.performer = performer
            binding.executePendingBindings()
        }
    }
}