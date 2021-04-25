package com.rysanek.pokeparse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rysanek.pokeparse.data.remote.models.Pokemon
import com.rysanek.pokeparse.databinding.SinglePokeMiniCardBinding

class PokeAdapter(private val glide: RequestManager): RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {
    
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
        val result = differList.currentList[position]
        holder.bind(result, glide)
    }
    
    override fun getItemCount() = differList.currentList.size
    
    private val differCallback = object: DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.abilities.id == newItem.abilities.id
        }
        
        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }
    
    val differList = AsyncListDiffer(this, differCallback)
    
}