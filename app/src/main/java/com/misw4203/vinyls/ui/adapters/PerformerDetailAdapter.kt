package com.misw4203.vinyls.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.misw4203.vinyls.R
import com.misw4203.vinyls.databinding.PerformerItemBinding
import com.misw4203.vinyls.models.Performer
import com.misw4203.vinyls.models.PerformerDetail

class PerformerDetailAdapter : RecyclerView.Adapter<PerformerDetailAdapter.PerformerDetailViewHolder>(){

    var PerformerDetail: PerformerDetail? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PerformerDetailViewHolder(val viewDataBinding: PerformerItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_detail_fragment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerDetailViewHolder {
        val withDataBinding: PerformerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PerformerDetailViewHolder.LAYOUT,
            parent,
            false)
        return PerformerDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: PerformerDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.performer = performerDetail
//            it.performer = performers[position]
        }
//        holder.viewDataBinding.root.setOnClickListener {
//            val action = PerformerFragmentDirections.actionPerformerFragmentToAlbumFragment()
//            // Navigate using that action
//            holder.viewDataBinding.root.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return 0
//        return performer.size
    }


}