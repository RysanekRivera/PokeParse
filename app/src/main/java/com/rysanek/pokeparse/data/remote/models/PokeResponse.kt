package com.rysanek.pokeparse.data.remote.models


import com.google.gson.annotations.SerializedName

data class PokeResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: MutableList<InitialResult>
)