package com.javaria.cats.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javaria.cats.data.network.CatApi
import com.javaria.cats.data.repositories.BaseRepository
import com.javaria.cats.data.repositories.CatRepository
import com.javaria.cats.ui.catFact.CatFactViewModel

class ViewModelFactory (
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CatFactViewModel::class.java) -> CatFactViewModel(repository as CatRepository) as T
            else -> throw IllegalAccessException("ViewModel Not Found")
        }
    }
}