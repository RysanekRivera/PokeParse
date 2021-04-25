package com.rysanek.pokeparse.di

import android.content.Context
import com.bumptech.glide.Glide
import com.rysanek.pokeparse.data.remote.apis.PokeApi
import com.rysanek.pokeparse.data.repository.PokeRepository
import com.rysanek.pokeparse.other.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun providePokeApi(): PokeApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokeApi::class.java)
    
    @Provides
    @Singleton
    fun providePokeRepository(api: PokeApi): PokeRepository = PokeRepository(api)
    
    @Provides
    @Singleton
    fun provideGlideInstance(@ApplicationContext context: Context) = Glide.with(context)
    
}