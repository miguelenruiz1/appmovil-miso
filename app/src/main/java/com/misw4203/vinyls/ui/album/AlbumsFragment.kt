package com.misw4203.vinyls.ui.album

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.misw4203.vinyls.R
import com.misw4203.vinyls.databinding.AlbumsFragmentBinding
import com.misw4203.vinyls.ui.adapters.AlbumsAdapter
import com.misw4203.vinyls.viewmodels.AlbumViewModel

class AlbumsFragment : Fragment(R.layout.albums_fragment) {

    private var _binding: AlbumsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel
    private lateinit var adapter: AlbumsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = AlbumsFragmentBinding.bind(view)

        // Configurar el ViewModel
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(requireActivity().application))
            .get(AlbumViewModel::class.java)

        // Configurar el adaptador y RecyclerView
        adapter = AlbumsAdapter { album ->
            val action = AlbumsFragmentDirections.actionAlbumsFragmentToAlbumDetailFragment(album.id!!)
            findNavController().navigate(action)
        }
        binding.albumsRv.layoutManager = LinearLayoutManager(context)
        binding.albumsRv.adapter = adapter

        // Configurar el botón para crear álbum
        binding.btnCreateAlbum.setOnClickListener {
            findNavController().navigate(R.id.createAlbumFragment)
        }


        // Observar la lista de álbumes
        viewModel.albums.observe(viewLifecycleOwner, Observer { albums ->
            adapter.albums = albums
        })

        // Manejar errores de red
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer { isError ->
            if (isError) {
                Toast.makeText(context, "Error fetching albums", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

