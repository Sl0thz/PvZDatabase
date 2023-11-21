package com.example.pvzdatabase.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pvzdatabase.data.model.FavoritePlant
import com.example.pvzdatabase.data.repository.PvZRepository
import com.example.pvzdatabase.ui.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: PvZRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<FavoritePlant>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    fun getFavoritePlantById(plantId : Int) {
        viewModelScope.launch {
            _result.value = Result.Loading
            _result.value = Result.Success(repository.getFavoritePlantById(plantId))
        }
    }

    fun updateFavorite(plantId : Int) {
        viewModelScope.launch {
            repository.updateFavoritePlant(plantId)
        }
    }
}