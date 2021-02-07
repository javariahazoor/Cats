package com.javaria.cats.data.repositories

import com.javaria.cats.data.network.CatApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepository @Inject constructor(private val api: CatApi): BaseRepository() {

    suspend fun getCatImage() = safeApiCall {
        api.getCatImage()
    }
    suspend fun getCatFact() = safeApiCall {
        api.getCatFacts("https://cat-fact.herokuapp.com/facts")
    }


}