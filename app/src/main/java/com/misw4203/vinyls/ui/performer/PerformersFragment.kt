package com.misw4203.vinyls.ui.performer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misw4203.vinyls.R
import com.misw4203.vinyls.databinding.PerformersFragmentBinding
import com.misw4203.vinyls.models.Collector
import com.misw4203.vinyls.models.Performer
import com.misw4203.vinyls.ui.adapters.PerformersAdapter
import com.misw4203.vinyls.viewmodels.PerformerViewModel


class PerformersFragment : Fragment(R.layout.performers_fragment) {

    private var _binding: PerformersFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: PerformersAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = PerformersFragmentBinding.bind(view)

        viewModelAdapter = PerformersAdapter()
        recyclerView = binding.performersRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        viewModel = ViewModelProvider(this, PerformerViewModel.Factory(requireActivity().application)).get(PerformerViewModel::class.java)


        viewModel.performers.observe(viewLifecycleOwner, Observer<List<Performer>> {
            it.apply {
                viewModelAdapter!!.performers = this
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