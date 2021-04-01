package com.rysanek.pokeparse.data.remote.models


import com.google.gson.annotations.SerializedName

data class InitialResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)