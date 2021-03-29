package com.rysanek.pokeparse.data.repository

import com.rysanek.pokeparse.data.remote.apis.PokeApi
import com.rysanek.pokeparse.data.remote.models.Ability
import com.rysanek.pokeparse.data.remote.models.PokeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class PokeRepository @Inject constructor (
   private val api: PokeApi
) {
    
    suspend fun getPokemon() = api.getPokemonResult()
    suspend fun getAbilities(name:String): Response<Ability> = api.getAbilities(name)
}