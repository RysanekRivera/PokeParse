package com.rysanek.pokeparse.data.local.entities

import com.rysanek.pokeparse.data.remote.models.Abilities

data class Pokemon(
    var name: String,
    var abilities: Abilities
)