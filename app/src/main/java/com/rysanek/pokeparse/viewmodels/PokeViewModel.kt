package com.rysanek.pokeparse.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rysanek.pokeparse.data.remote.Resource
import com.rysanek.pokeparse.data.remote.models.PokeResponse
import com.rysanek.pokeparse.data.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val repository: PokeRepository
): ViewModel() {
    
    private var pokeResponse: PokeResponse? = null
    private var _pokeList: MutableLiveData<Resource<PokeResponse>> = MutableLiveData()
    val pokeList = _pokeList
    
    init {
        getPokeList()
    }
    
    private fun getPokeList() = viewModelScope.launch {
        _pokeList.postValue(Resource.Loading())
       val response = repository.getPokemon()
        Log.d("pokeResult", response.body().toString())
        _pokeList.postValue(handlePokeResponse(response))

        _pokeList.value?.data?.results?.forEach {
            val abilities = repository.getAbilities(it.name)
            if (abilities.isSuccessful) {
                val abilitiesList = abilities.body()?.ability?.name
                Log.d("Abilities", abilitiesList.toString())
            }
        }
    }
    
    private fun handlePokeResponse(response: Response<PokeResponse>): Resource<PokeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                if (pokeResponse == null) {
                    pokeResponse = result
                } else {
                    val oldList = pokeResponse?.results
                    val newList = result.results
                    oldList?.addAll(newList)
                }
                
                return Resource.Success(pokeResponse ?: result)
            }
        }
        
        return Resource.Error(response.message())
    }
}