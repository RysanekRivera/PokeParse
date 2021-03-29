package com.rysanek.pokeparse.data.remote.models


import com.google.gson.annotations.SerializedName

data class Move(
    @SerializedName("move")
    val move: MoveX
)