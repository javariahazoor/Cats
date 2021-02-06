package com.javaria.cats.data.network

import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * This class will receive retrofit client
 */
class RemoteDataSource {
    companion object {
        private const val BASE_URL = "https://api.thecatapi.com/v1/"
        private const val CAT_FACTS_BASE_URL = "https://api.thecatapi.com/v1/"
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * This function is a generic function and will create a retrofit client
     */
    fun <Api> buildApi(
        api: Class<Api>,
        authApi: String? = null
    ): Api {

        val httpClient = OkHttpClient.Builder()

        if (authApi != null) {
            httpClient.addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().also {
                        //here token is passes
                        //or agr bagair parameters k agr
                        it.addHeader("x-api-key", "api_key=$authApi")
                    }.build()
                )
            }
            //adding interceptor for debugging
            httpClient.addNetworkInterceptor(StethoInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            //adding interceptor for posting token to each call

            .client(
                httpClient.also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(api)

    }
}