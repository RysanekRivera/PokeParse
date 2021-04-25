package com.rysanek.pokeparse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rysanek.pokeparse.data.remote.models.Pokemon
import com.rysanek.pokeparse.databinding.SinglePokeMiniCardBinding
import com.rysanek.pokeparse.viewmodels.PokeViewModel
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class PokeAdapter(
    private val glide: RequestManager,
    private val pokeViewModel: PokeViewModel
): RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {
    private val currentPokemonList= mutableListOf<Pokemon>()
    
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
            
            binding.tvPokemonName.text = pokemon.name
            binding.chType1.text = type1
            binding.chType2.text = type2
            glide.load(pokemon.abilities.sprites.frontDefault).into(binding.ivPokeImage)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        return PokeViewHolder.from(parent)
    }
    
    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val result = currentPokemonList[position]
        holder.bind(result, glide)
        if (holder.layoutPosition == currentPokemonList.lastIndex - 5) {
            pokeViewModel.nextPage()
        }
    }
    
    override fun getItemCount() = currentPokemonList.size
    
    private fun diffCallback(newList: List<Pokemon>) = object: DiffUtil.Callback(){
        override fun getOldListSize() = currentPokemonList.size
    
        override fun getNewListSize() = newList.size
    
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return currentPokemonList[oldItemPosition].name === newList[newItemPosition].name
        }
    
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return currentPokemonList[oldItemPosition] == newList[newItemPosition]
        }
    }
    
    fun setData(newList: List<Pokemon>) {
        val diffResult = DiffUtil.calculateDiff(diffCallback(newList))
        currentPokemonList.clear()
        currentPokemonList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}