package com.rysanek.pokeparse.data.remote.apis

import com.rysanek.pokeparse.data.remote.models.Abilities
import com.rysanek.pokeparse.data.remote.models.PokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon/")
    suspend fun getPokemonResult(
        @Query("offset") offSet:Int
    ): Response<PokeResponse>
    
    @GET("pokemon/{name}")
    suspend fun getAbilities(
        @Path("name") name: String
    ): Response<Abilities>

}