package com.rysanek.pokeparse.data.repository

import android.util.Log
import com.rysanek.pokeparse.data.remote.apis.PokeApi
import com.rysanek.pokeparse.data.remote.models.Pokemon
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val api: PokeApi
) {
    fun getPokemon() = flow {
        val pokeResult = api.getPokemonResult()
        if (pokeResult.isSuccessful) {
            pokeResult.body()?.let {
                emit(it.results)
            }
        }
        Log.d("Repository", "result successful: ${pokeResult.isSuccessful}")
    }
    
    fun getAbilities(name: String) = flow {
        val networkResponse = api.getAbilities(name)
        Log.d("Repository", "getAbilities successful: ${networkResponse.isSuccessful}")
        if (networkResponse.isSuccessful){
            networkResponse.body()?.let { abilities -> emit(Pokemon(name, abilities)) }
        }
    }
}