package com.misw4203.vinyls.ui.performer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misw4203.vinyls.databinding.PerformerDetailFragmentBinding
import com.misw4203.vinyls.models.PerformerDetail
import com.misw4203.vinyls.ui.adapters.PerformerDetailAdapter
import com.misw4203.vinyls.viewmodels.PerformerDetailViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PerformerDetailFragment : Fragment() {

    private var _binding: PerformerDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var albumRecyclerView: RecyclerView
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var noAlbumsView: TextView
    private lateinit var noCommentsView: TextView
    private lateinit var viewModel: PerformerDetailViewModel
    private var viewModelAdapter: PerformerDetailAdapter? = null

    private val args: PerformerDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PerformerDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = PerformerDetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Sets the album recycler view
        albumRecyclerView = binding.albumsRecyclerView
        albumRecyclerView.layoutManager = LinearLayoutManager(context)
        albumRecyclerView.adapter = viewModelAdapter

        // Sets the comment recycler view
        commentRecyclerView = binding.commentsRecyclerView
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        commentRecyclerView.adapter = viewModelAdapter

        // Sets the empty views
        noAlbumsView = binding.noAlbumsView
        noCommentsView = binding.noCommentsView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, PerformerDetailViewModel.Factory(activity.application)).get(
            PerformerDetailViewModel::class.java
        )
        viewModel.performerDetail.observe(viewLifecycleOwner, Observer<PerformerDetail> {
            it.apply {
                Log.d("PERFORMER Detail IMG", this.toString())
                viewModelAdapter!!.performerDetail = this
                binding.performerDetail = this
                toggleEmptyViews()
            }
        })
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
        viewModel.retrievePerformerDetail(args.performerId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun toggleEmptyViews() {
        if (viewModelAdapter!!.itemCount == 0) {
            noAlbumsView.visibility = View.VISIBLE
            noCommentsView.visibility = View.VISIBLE
            albumRecyclerView.visibility = View.GONE
            commentRecyclerView.visibility = View.GONE
        } else {
            noAlbumsView.visibility = View.GONE
            noCommentsView.visibility = View.GONE
            albumRecyclerView.visibility = View.VISIBLE
            commentRecyclerView.visibility = View.VISIBLE
        }
    }

}