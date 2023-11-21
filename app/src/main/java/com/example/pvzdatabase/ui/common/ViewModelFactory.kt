package com.example.pvzdatabase.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pvzdatabase.data.repository.PvZRepository
import com.example.pvzdatabase.ui.screen.detail.DetailViewModel
import com.example.pvzdatabase.ui.screen.favorite.FavoriteViewModel
import com.example.pvzdatabase.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: PvZRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(repository) as T
            DetailViewModel::class.java -> DetailViewModel(repository) as T
            FavoriteViewModel::class.java -> FavoriteViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
}