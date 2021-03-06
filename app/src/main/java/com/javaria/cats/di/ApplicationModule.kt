package com.javaria.cats.di

import com.javaria.cats.data.network.CatApi
import com.javaria.cats.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    fun provideCatApi(): CatApi {
        return RemoteDataSource().buildApi(CatApi::class.java)
    }
}