package com.rysanek.pokeparse.data.repository

import com.rysanek.pokeparse.data.remote.apis.PokeApi
import com.rysanek.pokeparse.other.Constants.OFFSET
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScoped
class PokeRepository @Inject constructor(
    private val api: PokeApi
) {
    
    suspend fun getPokemon(offSet: Int = OFFSET) = flow {
        emit(api.getPokemonResult(offSet))
    }
    
    suspend fun getAbilities(pokemonName: String) = flow {
        emit(api.getAbilities(pokemonName))
    }
}