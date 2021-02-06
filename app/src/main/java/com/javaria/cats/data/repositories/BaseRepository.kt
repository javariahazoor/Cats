package com.javaria.cats.data.repositories

import com.javaria.cats.data.network.ResultResource
import com.javaria.cats.data.network.ResultResource.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

//this will be base repository for all repositories
abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): ResultResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else ->
                        Failure(true, null, null)
                }
            }
        }
    }
}