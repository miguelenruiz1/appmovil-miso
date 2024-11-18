package com.misw4203.vinyls.ui.collector

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
import com.misw4203.vinyls.databinding.CollectorDetailFragmentBinding
import com.misw4203.vinyls.models.CollectorDetail
import com.misw4203.vinyls.ui.adapters.CollectorDetailAlbumsAdapter
import com.misw4203.vinyls.ui.adapters.CollectorDetailCommentsAdapter
import com.misw4203.vinyls.viewmodels.CollectorDetailViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CollectorDetailFragment : Fragment() {

    private var _binding: CollectorDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var albumRecyclerView: RecyclerView
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var noAlbumsView: TextView
    private lateinit var noCommentsView: TextView
    private lateinit var viewModel: CollectorDetailViewModel
    private var albumsAdapter: CollectorDetailAlbumsAdapter? = null
    private var commentsAdapter: CollectorDetailCommentsAdapter? = null

    private val args: CollectorDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        albumsAdapter = CollectorDetailAlbumsAdapter()
        commentsAdapter = CollectorDetailCommentsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Sets the album recycler view
        albumRecyclerView = binding.albumsRecyclerView
        albumRecyclerView.layoutManager = LinearLayoutManager(context)
        albumRecyclerView.adapter = albumsAdapter

        // Sets the comment recycler view
        commentRecyclerView = binding.commentsRecyclerView
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        commentRecyclerView.adapter = commentsAdapter

        // Sets the empty views
        noAlbumsView = binding.noAlbumsView
        noCommentsView = binding.noCommentsView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, CollectorDetailViewModel.Factory(activity.application)).get(
            CollectorDetailViewModel::class.java
        )
        viewModel.collectorDetail.observe(viewLifecycleOwner, Observer<CollectorDetail> {
            it.apply {
                Log.d("COLLECTOR Detail IMG", this.toString())
                albumsAdapter!!.albums = this.collectorAlbums
                commentsAdapter!!.comments = this.comments
                binding.collectorDetail = this
                toggleEmptyViews()
            }
        })
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
        viewModel.retrieveCollectorDetail(args.collectorId)
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
        if (albumsAdapter!!.itemCount == 0) {
            noAlbumsView.visibility = View.VISIBLE
            albumRecyclerView.visibility = View.GONE
        } else {
            noAlbumsView.visibility = View.GONE
            albumRecyclerView.visibility = View.VISIBLE
        }
        if (commentsAdapter!!.itemCount == 0) {
            noCommentsView.visibility = View.VISIBLE
            commentRecyclerView.visibility = View.GONE
        } else {
            noCommentsView.visibility = View.GONE
            commentRecyclerView.visibility = View.VISIBLE
        }
    }

}