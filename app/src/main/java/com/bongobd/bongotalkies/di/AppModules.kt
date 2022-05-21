package com.bongobd.bongotalkies.di

import android.content.Context
import com.bongobd.bongotalkies.common.Constants
import com.bongobd.bongotalkies.data.data_repository.DataRepository
import com.bongobd.bongotalkies.data.remote.MoviesApi
import com.bongobd.bongotalkies.domain.repository.ApiRepository
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModules {


    @Singleton
    @Provides
    fun provideOkHttp(
        @ApplicationContext context: Context
    ) = OkHttpClient
        .Builder()
        .addInterceptor(
            ChuckerInterceptor(context)
        )
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): MoviesApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MoviesApi::class.java)
    }


    @Provides
    @Singleton
    fun provideApiRepository(api : MoviesApi): ApiRepository {
        return DataRepository(api)
    }
}