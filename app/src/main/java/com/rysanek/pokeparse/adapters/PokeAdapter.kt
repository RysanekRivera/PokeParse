package com.rysanek.pokeparse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.pokeparse.data.remote.models.Result
import com.rysanek.pokeparse.databinding.SinglePokeMiniCardBinding

class PokeAdapter: RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {
    
    private val differCallback = object: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    
    val differList = AsyncListDiffer(this, differCallback)
    
    class PokeViewHolder(private val binding: SinglePokeMiniCardBinding): RecyclerView.ViewHolder(binding.root){
        
        companion object{
            fun from(parent: ViewGroup): PokeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val holder = SinglePokeMiniCardBinding.inflate(layoutInflater, parent, false)
                
                return PokeViewHolder(holder)
            }
        }
        
        fun bind(result: Result) {
            binding.tvPokemonName.text = result.name
            
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        return PokeViewHolder.from(parent)
    }
    
    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val result = differList.currentList[position]
        holder.bind(result)
    }
    
    override fun getItemCount() = differList.currentList.size
    
    
}