package com.rysanek.pokeparse.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.rysanek.pokeparse.adapters.PokeAdapter
import com.rysanek.pokeparse.data.local.entities.Pokemon
import com.rysanek.pokeparse.databinding.FragmentPokeListBinding
import com.rysanek.pokeparse.utils.Resource
import com.rysanek.pokeparse.utils.hide
import com.rysanek.pokeparse.utils.show
import com.rysanek.pokeparse.utils.showSnackBar
import com.rysanek.pokeparse.viewmodels.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "PokeListFragment"

@AndroidEntryPoint
class PokeListFragment: Fragment() {
    
    private lateinit var binding: FragmentPokeListBinding
    private val pokeViewModel: PokeViewModel by viewModels()
    private lateinit var pokeAdapter: PokeAdapter
    val pokemonList = mutableListOf<Pokemon>()
    
    @Inject
    lateinit var glide: RequestManager
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokeListBinding.inflate(layoutInflater)
        
        setupRecyclerView()
        
        pokeViewModel.getPokemonStatus.observe(viewLifecycleOwner) { response ->
           handleInitialResponse(response)
        }
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        pokeAdapter = PokeAdapter(glide, pokeViewModel)
        binding.rvPokemonList.apply {
            adapter = pokeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
    
    private fun handleInitialResponse(response: Resource<List<Pokemon>>) {
        when (response) {
            is Resource.Success -> {
                Log.d("Fragment", "Resource Success called")
                response.data?.let { pokeAdapter.setData(it) }
                binding.pokeProgressBar.hide()
            }
            is Resource.Error -> {
                showSnackBar(response.message.toString())
                binding.pokeProgressBar.hide()
            }
            is Resource.Loading -> {
                binding.pokeProgressBar.show()
            }
            is Resource.Idle -> {
                binding.pokeProgressBar.hide()
            }
        }
    }
    
}