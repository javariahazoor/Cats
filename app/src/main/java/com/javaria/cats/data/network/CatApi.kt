package com.javaria.cats.data.network

import com.javaria.cats.data.models.CatFactsResponse
import com.javaria.cats.data.models.CatImageResponse
import retrofit2.http.GET
import retrofit2.http.Url
interface CatApi {

    @GET("images/search")
    suspend fun getCatImage():List<CatImageResponse>
    @GET
    suspend fun getCatFacts(@Url CatFactUrl: String):List<CatFactsResponse>

}
