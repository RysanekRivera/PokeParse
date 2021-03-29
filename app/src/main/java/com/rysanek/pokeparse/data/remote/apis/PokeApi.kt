package com.rysanek.pokeparse.data.remote.apis

import com.rysanek.pokeparse.data.remote.models.Ability
import com.rysanek.pokeparse.data.remote.models.PokeResponse
import com.rysanek.pokeparse.other.Constants.OFFSET
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon/")
    suspend fun getPokemonResult(
        @Query("offset") offSet:Int = OFFSET
    ): Response<PokeResponse>
    
    @GET("pokemon/")
    suspend fun getAbilities(
        @Query("") name: String
    ): Response<Ability>

}