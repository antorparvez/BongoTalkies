package com.bongobd.bongotalkies.di

import com.bongobd.bongotalkies.common.Constants
import com.bongobd.bongotalkies.data.data_repository.DataRepository
import com.bongobd.bongotalkies.data.remote.MoviesApi
import com.bongobd.bongotalkies.domain.repository.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun provideRetrofit(): MoviesApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MoviesApi::class.java)
    }


    @Provides
    @Singleton
    fun provideApiRepository(api : MoviesApi): ApiRepository {
        return DataRepository(api)
    }
}