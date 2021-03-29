package com.rysanek.pokeparse.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.rysanek.pokeparse.R
import com.rysanek.pokeparse.adapters.PokeAdapter
import com.rysanek.pokeparse.data.remote.Resource
import com.rysanek.pokeparse.databinding.FragmentPokeListBinding
import com.rysanek.pokeparse.viewmodels.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "PokeListFragment"
@AndroidEntryPoint
class PokeListFragment : Fragment() {
    private lateinit var binding: FragmentPokeListBinding
    private val pokeViewModel: PokeViewModel by viewModels()
    private lateinit var pokeAdapter: PokeAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pokeAdapter = PokeAdapter()
        binding = FragmentPokeListBinding.inflate(layoutInflater)
        setupRecyclerView()
        
        pokeViewModel.pokeList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { newsResponse ->
                        pokeAdapter.differList.submitList(newsResponse.results)
                    }
                }
                is Resource.Error   -> {
                    
                    response.message?.let { message ->
                        Log.d(TAG, message)
                    }
                }
                is Resource.Loading -> {
                
                }
            }
        }
        return binding.root
    }
    
    private fun setupRecyclerView() {
        binding.rvPokemonList.apply {
            adapter = pokeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            
        }
        
    }


}