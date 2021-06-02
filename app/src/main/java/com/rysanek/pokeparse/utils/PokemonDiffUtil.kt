package com.rysanek.pokeparse.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.pokeparse.data.local.entities.Pokemon

class PokemonDiffUtil(private val oldList: List<Pokemon>, private val newList: List<Pokemon>): DiffUtil.Callback() {
    
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList [newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList [newItemPosition]
    }

    fun <T: RecyclerView.ViewHolder> calculateDiff(adapter: RecyclerView.Adapter<T>) =
        DiffUtil.calculateDiff(this).dispatchUpdatesTo(adapter)
}