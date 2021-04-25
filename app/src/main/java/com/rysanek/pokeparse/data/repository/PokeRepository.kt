package com.rysanek.pokeparse.data.repository

import android.util.Log
import com.rysanek.pokeparse.data.remote.apis.PokeApi
import com.rysanek.pokeparse.data.remote.models.Pokemon
import com.rysanek.pokeparse.other.Constants.OFFSET
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val api: PokeApi
) {
    fun getPokemon(offSet:Int = OFFSET) = flow {
        val networkResponse = api.getPokemonResult(offSet)
        Log.d("pokeRepository", "response: ${networkResponse.raw()}")
        if (networkResponse.isSuccessful) {
            Log.d("Repository", "result successful: ${networkResponse.isSuccessful}")
            networkResponse.body()?.let { pokeResponse ->
                emit(pokeResponse.results)
            }
        } else {
            Log.d("Repository", "result Error: ${networkResponse.message()}")
        }
    }
    
    fun getAbilities(pokemonName: String) = flow {
        val networkResponse = api.getAbilities(pokemonName)
       
        if (networkResponse.isSuccessful){
            networkResponse.body()?.let { abilities -> emit(Pokemon(pokemonName, abilities)) }
            Log.d("Repository", "getAbilities successful: ${networkResponse.isSuccessful}")
        } else {
            Log.d("Repository", "result Error: ${networkResponse.message()}")
        }
    }
}