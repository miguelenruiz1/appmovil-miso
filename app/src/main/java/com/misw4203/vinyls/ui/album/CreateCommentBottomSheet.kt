package com.misw4203.vinyls.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.misw4203.vinyls.databinding.BottomSheetCreateCommentBinding
import com.misw4203.vinyls.viewmodel.CreateCommentViewModel


class CreateCommentBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val ALBUM_ID = "albumId"

        fun forAlbum(albumId: Int): CreateCommentBottomSheet {
            val fragment = CreateCommentBottomSheet()
            val args = Bundle().apply {
                putInt(ALBUM_ID, albumId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: BottomSheetCreateCommentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateCommentViewModel

    fun setCommentTextError(msg: String) {
        binding.etComment.error = msg
    }

    fun validateFields(): Boolean {
        var valid = true
        val comment = binding.etComment.text.toString()
        if (comment.isBlank()) {
            setCommentTextError("El comentario no puede estar vacío")
            valid = false
        } else {
            setCommentTextError("")
        }

        val rating = binding.ratingBar.rating
        if (rating == 0f) {
            binding.ratingError.visibility = View.VISIBLE
            valid = false
        }
        return valid
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCreateCommentBinding.inflate(inflater, container, false)
        // Configurar el ViewModel
        viewModel = ViewModelProvider(this, CreateCommentViewModel.Factory(requireActivity().application))
            .get(CreateCommentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el rating bar
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            if (rating == 0f) {
                binding.ratingError.visibility = View.VISIBLE
            } else {
                binding.ratingError.visibility = View.GONE
            }
        }

        // Configurar el botón para crear el álbum
        binding.btnAdd.setOnClickListener {
            if (!validateFields()) {
                return@setOnClickListener
            }

            val albumId = arguments?.getInt(ALBUM_ID)
            val rating = binding.ratingBar.rating
            val comment = binding.etComment.text.toString()
            val collectorId = 100
            viewModel.createComment(
                albumId ?: 100,
                collectorId,
                comment,
                rating.toInt()
            )
            this.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
