package com.misw4203.vinyls.ui.album

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.misw4203.vinyls.databinding.ActivityCreateAlbumBinding
import com.misw4203.vinyls.models.createAlbum
import com.misw4203.vinyls.repositories.CreateAlbumRepository
import com.misw4203.vinyls.R
import com.misw4203.vinyls.repositories.AlbumsRepository


class CreateAlbumFragment : Fragment() {

    private var _binding: ActivityCreateAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: AlbumsRepository
    private lateinit var selectedGenre: String
    private lateinit var selectedRecordLabel: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityCreateAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el repositorio
        repository = AlbumsRepository(activity?.application ?: Application())

        // Configurar el Spinner para el género
        val genres = resources.getStringArray(R.array.genre_array)
        val genreAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genres)
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenre.adapter = genreAdapter

        // Capturar el género seleccionado
        binding.spinnerGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedGenre = genres[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedGenre = genres[0] // Valor predeterminado
            }
        }

        // Configurar el Spinner para el sello discográfico
        val recordLabels = resources.getStringArray(R.array.record_label_array)
        val recordLabelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, recordLabels)
        recordLabelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRecordLabel.adapter = recordLabelAdapter

        // Capturar el sello discográfico seleccionado
        binding.spinnerRecordLabel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedRecordLabel = recordLabels[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedRecordLabel = recordLabels[0] // Valor predeterminado
            }
        }

        // Configurar el botón para crear el álbum
        binding.btnCreateAlbum.setOnClickListener {
            val name = binding.etAlbumName.text.toString()
            val cover = binding.etAlbumCover.text.toString()
            val releaseDate = binding.etAlbumReleaseDate.text.toString()
            val description = binding.etAlbumDescription.text.toString()

            if (name.isBlank() || cover.isBlank() || releaseDate.isBlank() || description.isBlank()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el objeto álbum
            val album = createAlbum(
                name = name,
                cover = cover,
                releaseDate = "${releaseDate}T00:00:00-05:00", // Ajuste de formato
                description = description,
                genre = selectedGenre,
                recordLabel = selectedRecordLabel
            )

            // Llamar al repositorio para crear el álbum
            repository.createAlbum(
                album = album,
                onSuccess = {
                    Toast.makeText(requireContext(), "Álbum creado exitosamente: ${it.name}", Toast.LENGTH_LONG).show()
                    parentFragmentManager.popBackStack()
                },
                onError = {
                    Toast.makeText(requireContext(), "Error al crear el álbum: $it", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
