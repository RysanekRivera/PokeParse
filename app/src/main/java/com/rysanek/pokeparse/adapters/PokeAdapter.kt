package com.rysanek.pokeparse.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rysanek.pokeparse.R
import com.rysanek.pokeparse.data.local.entities.Pokemon
import com.rysanek.pokeparse.databinding.SinglePokeMiniCardBinding
import com.rysanek.pokeparse.utils.PokemonDiffUtil
import com.rysanek.pokeparse.utils.gone
import com.rysanek.pokeparse.viewmodels.PokeViewModel

class PokeAdapter(
    private val glide: RequestManager,
    private val pokeViewModel: PokeViewModel
): RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {
    
    private var currentPokemonList = mutableListOf<Pokemon>()
    
    class PokeViewHolder(private val binding: SinglePokeMiniCardBinding): RecyclerView.ViewHolder(binding.root) {
        
        companion object {
            
            fun from(parent: ViewGroup): PokeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val holder = SinglePokeMiniCardBinding.inflate(layoutInflater, parent, false)
                return PokeViewHolder(holder)
            }
        }
        
        fun bind(pokemon: Pokemon, glide: RequestManager) {
            val type1 = pokemon.abilities.types[0].type?.name ?: ""
            val type2 = if (pokemon.abilities.types.size > 1) pokemon.abilities.types[1].type?.name else ""
            
            binding.cvPokeCard.background.apply {
                when(type1){
                    "fire" -> setTint(ContextCompat.getColor(binding.root.context,R.color.fire))
                    "grass" -> setTint(ContextCompat.getColor(binding.root.context,R.color.grass))
                    "poison" -> setTint(ContextCompat.getColor(binding.root.context,R.color.poison))
                    "water" -> setTint(ContextCompat.getColor(binding.root.context,R.color.water))
                    "electric" -> setTint(ContextCompat.getColor(binding.root.context,R.color.yellow))
                    "normal" -> setTint(ContextCompat.getColor(binding.root.context,R.color.normal))
                    "flying" -> setTint(ContextCompat.getColor(binding.root.context,R.color.flying))
                    "fairy" -> setTint(ContextCompat.getColor(binding.root.context,R.color.fairy))
                    "fighting" -> setTint(ContextCompat.getColor(binding.root.context,R.color.fighting))
                    "steel" -> setTint(ContextCompat.getColor(binding.root.context,R.color.steel))
                    "ghost" -> setTint(ContextCompat.getColor(binding.root.context,R.color.ghost))
                    "ground" -> setTint(ContextCompat.getColor(binding.root.context,R.color.ground))
                    "ice" -> setTint(ContextCompat.getColor(binding.root.context,R.color.ice))
                    "psychic" -> setTint(ContextCompat.getColor(binding.root.context,R.color.psychic))
                    "rock" -> setTint(ContextCompat.getColor(binding.root.context,R.color.rock))
                    "dark" -> setTint(ContextCompat.getColor(binding.root.context,R.color.dark))
                    "bug" -> setTint(ContextCompat.getColor(binding.root.context,R.color.bug))
                    "dragon" -> setTint(ContextCompat.getColor(binding.root.context,R.color.dragon))
                }
            }
            
            binding.tvPokemonName.text = pokemon.name
            binding.chType1.text = type1
            
            
            if (type2 == null || type2 == "") binding.chType2.gone() else binding.chType2.text = type2
            glide.load(pokemon.abilities.sprites.frontDefault).into(binding.ivPokeImage)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        return PokeViewHolder.from(parent)
    }
    
    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val result = currentPokemonList[position]
        holder.bind(result, glide)
        if (holder.layoutPosition == currentPokemonList.lastIndex - 5 ) {
            pokeViewModel.nextPage()
        }
    }
    
    override fun getItemCount() = currentPokemonList.size
    
    fun setData(newList: List<Pokemon>) {
        val oldList = if (currentPokemonList.isNullOrEmpty()) mutableListOf() else currentPokemonList
        Log.d("Adapter", "currentList: ${currentPokemonList.size}, newList: ${newList.size}")
        PokemonDiffUtil(oldList, newList).calculateDiff(this)
        newList.forEach { pokemon ->
            if (!currentPokemonList.contains(pokemon)) {
                currentPokemonList.add(pokemon)
            }
        }
        Log.d("Adapter", "currentList: ${currentPokemonList.size}, newList: ${newList.size}")
    }
}