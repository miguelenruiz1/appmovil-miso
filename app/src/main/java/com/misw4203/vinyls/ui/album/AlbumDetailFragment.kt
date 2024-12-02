package com.misw4203.vinyls.ui.album

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.misw4203.vinyls.R
import com.misw4203.vinyls.databinding.FragmentAlbumDetailBinding
import com.misw4203.vinyls.ui.adapters.TracksAdapter
import com.misw4203.vinyls.viewmodels.AlbumViewModel

class AlbumDetailFragment : Fragment(R.layout.fragment_album_detail) {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding: FragmentAlbumDetailBinding
        get() = _binding ?: throw IllegalStateException("Binding should not be null")
    private lateinit var viewModel: AlbumViewModel
    private val adapter = TracksAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumDetailBinding.bind(view)

        // Configurar el ViewModel
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(requireActivity().application))
            .get(AlbumViewModel::class.java)

        // Configurar RecyclerView
        binding.tracksRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.tracksRecyclerView.adapter = adapter

        // Observar los detalles del álbum
        viewModel.albumDetail.observe(viewLifecycleOwner, Observer { album ->
            binding.album = album
            adapter.tracks = album.tracks
            // Configurar el botón para crear comentario
            binding.btnCommentCreate.setOnClickListener {
                val bottomSheet = CreateCommentBottomSheet.forAlbum(album.id ?: 100)
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
            }
        })

        // Obtener los detalles del álbum
        val albumId = AlbumDetailFragmentArgs.fromBundle(requireArguments()).albumId
        viewModel.getAlbumDetails(albumId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
