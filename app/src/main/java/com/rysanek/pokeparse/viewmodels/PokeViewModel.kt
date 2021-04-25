package com.rysanek.pokeparse.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.pokeparse.data.remote.models.Pokemon
import com.rysanek.pokeparse.data.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository
): ViewModel() {
    
    private var _pokemon: MutableLiveData<MutableList<Pokemon>> = MutableLiveData()
    val pokemon: LiveData<MutableList<Pokemon>> = _pokemon
    
    private val pokemonList = mutableListOf<Pokemon>()
    
    init {
        getPokemon()
    }
    
    private fun getPokemon() = viewModelScope.launch {
        repository.getPokemon()
            .catch { e -> Log.d("PokeViewModel", "error: ${e.message}") }
            .onCompletion {
                _pokemon.postValue(pokemonList)
                Log.d("PokeViewModel", "getPokemon Completed")
            }
            .collect { initialResult ->
                initialResult.forEach { pokemon ->
                    getAbilities(pokemon.name).join()
                    
                }
                Log.d("PokeViewModel", "initial result size ${initialResult.size}")
            }
    }
    
    private fun getAbilities(name: String) = viewModelScope.launch {
        repository.getAbilities(name)
            .catch { e -> Log.d("PokeViewModel", "getAbilities error: ${e.message}") }
            .collect { pokemon ->
                Log.d("PokeViewModel", "getAbilities name: ${pokemon.name}")
                pokemonList.add(pokemon)
                Log.d("PokeViewModel", "pokemonList size: ${pokemonList.size}")
            }
    }
    
}
