package com.misw4203.vinyls.ui.album

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misw4203.vinyls.R
import com.misw4203.vinyls.databinding.AlbumsFragmentBinding
import com.misw4203.vinyls.models.Album
import com.misw4203.vinyls.ui.adapters.AlbumsAdapter
import com.misw4203.vinyls.viewmodels.AlbumViewModel

class AlbumsFragment : Fragment(R.layout.albums_fragment) {

    private var _binding: AlbumsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = AlbumsFragmentBinding.bind(view)

        viewModelAdapter = AlbumsAdapter()
        recyclerView = binding.albumsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(requireActivity().application)).get(AlbumViewModel::class.java)


        viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                viewModelAdapter!!.albums = this
            }
        })


        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
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
}
