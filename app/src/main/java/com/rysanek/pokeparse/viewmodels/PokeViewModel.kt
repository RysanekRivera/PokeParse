package com.rysanek.pokeparse.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.pokeparse.data.local.entities.Pokemon
import com.rysanek.pokeparse.data.repository.PokeRepository
import com.rysanek.pokeparse.other.Constants.NEXT_OFFSET
import com.rysanek.pokeparse.other.Constants.OFFSET
import com.rysanek.pokeparse.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository
): ViewModel() {
    
    private val _pokemonDownloadStatus: MutableLiveData<Resource<List<Pokemon>>> =
        MutableLiveData(Resource.Loading())
    val getPokemonStatus: LiveData<Resource<List<Pokemon>>> = _pokemonDownloadStatus
    
    private val pokemonList: MutableList<Pokemon> = mutableListOf()
    
    private var paginationOffset: Int = OFFSET
    
    init {
        getPokemon()
    }
    
    private fun getPokemon(offSet: Int = paginationOffset) = viewModelScope.launch {
        
        _pokemonDownloadStatus.postValue(Resource.Loading())
        repository.getPokemon(offSet)
            .catch { e ->
                _pokemonDownloadStatus.postValue(Resource.Error(e.message.toString()))
                Log.d("PokeViewModel", "error: ${e.message}")
            }
            .filter { response ->
                response.isSuccessful && response.body() != null
            }
            .onCompletion {
                _pokemonDownloadStatus.postValue(Resource.Success(pokemonList))
            }
            .collect { response ->
                response.body()!!.results.forEach { pokemon ->
                    getAbilities(pokemon.name).join()
                }
            }
    }
    
    private fun getAbilities(name: String) = viewModelScope.launch {
        repository.getAbilities(name)
            .catch { e ->
                _pokemonDownloadStatus.postValue(Resource.Error(e.message.toString()))
                Log.d("PokeViewModel", "getAbilities error: ${e.message}")
            }
            .filter { response ->
                response.isSuccessful && response.body() != null
            }
            .collect { response ->
                val abilities = response.body()!!
                pokemonList.add(
                    Pokemon(
                        name.capitalize(Locale.ROOT),
                        abilities
                    )
                )
                Log.d("PokeViewModel", "getAbilities name: $name, number: ${abilities.id}")
            }
        
    }
    
    fun nextPage() {
        paginationOffset += NEXT_OFFSET
        getPokemon(paginationOffset)
        Log.d("ViewModel", "pagination: $paginationOffset, nextOffset: $NEXT_OFFSET")
    }
    
}
