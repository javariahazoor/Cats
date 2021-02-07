package com.javaria.cats.ui.catFact


import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javaria.cats.data.models.CatFactsResponse
import com.javaria.cats.data.models.CatImageResponse
import com.javaria.cats.data.network.ResultResource
import com.javaria.cats.data.repositories.CatRepository
import com.javaria.cats.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import androidx.hilt.lifecycle.ViewModelInject


class CatFactViewModel @ViewModelInject constructor(private val repository: CatRepository) : BaseViewModel (repository)  {
    private val _catImageResponse = MutableLiveData<ResultResource<List<CatImageResponse>>>()
    val catImageResponse: LiveData<ResultResource<List<CatImageResponse>>>
        get() = _catImageResponse

    /**
     * This function will get the random image of cat
     */
    suspend fun getCatImage() = viewModelScope.launch {
        _catImageResponse.value = ResultResource.Loading
        _catImageResponse.value = repository.getCatImage()
    }

    private val _catFactsResponse = MutableLiveData<ResultResource<List<CatFactsResponse>>>()
    val catFactsResponse: LiveData<ResultResource<List<CatFactsResponse>>>
        get() = _catFactsResponse

    /**
     * This function will get the random image of cat
     */
    suspend fun getCatFacts() = viewModelScope.launch {
        _catFactsResponse.value = ResultResource.Loading
        _catFactsResponse.value = repository.getCatFact()
    }


}