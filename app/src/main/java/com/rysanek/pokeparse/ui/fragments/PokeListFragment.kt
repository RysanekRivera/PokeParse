package com.rysanek.pokeparse.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.rysanek.pokeparse.adapters.PokeAdapter
import com.rysanek.pokeparse.databinding.FragmentPokeListBinding
import com.rysanek.pokeparse.viewmodels.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "PokeListFragment"

@AndroidEntryPoint
class PokeListFragment: Fragment() {
    
    private lateinit var binding: FragmentPokeListBinding
    private val pokeViewModel: PokeViewModel by viewModels()
    private lateinit var pokeAdapter: PokeAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokeListBinding.inflate(layoutInflater)
        
        setupRecyclerView()
        
        pokeViewModel.pokemon.observe(viewLifecycleOwner) { pokemonList ->
            Log.d(TAG, "pokemonList: ${pokemonList.size}")
            pokeAdapter.differList.submitList(pokemonList)
        }
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        pokeAdapter = PokeAdapter()
        binding.rvPokemonList.apply {
            adapter = pokeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
    
}