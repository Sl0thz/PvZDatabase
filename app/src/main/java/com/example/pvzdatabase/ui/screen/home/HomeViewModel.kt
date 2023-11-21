package com.example.pvzdatabase.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pvzdatabase.data.model.FavoritePlant
import com.example.pvzdatabase.data.repository.PvZRepository
import com.example.pvzdatabase.ui.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PvZRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<List<FavoritePlant>>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _noMatch = MutableStateFlow(false)
    val noMatch = _noMatch.asStateFlow()

    fun getAllPlants() {
        viewModelScope.launch {
            repository.getAllFavoritePlant()
                .catch { exception ->
                    _result.value = Result.Error(exception.message.toString())
                }
                .collect { favoritePlant ->
                    _result.value = Result.Success(favoritePlant)
                }
        }
    }

    fun searchPlant(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.searchPlant(query)
                .catch { exception ->
                    _result.value = Result.Error(exception.message.toString())
                }
                .collect { favoritePlant ->
                    if (favoritePlant.isEmpty()) {
                        _noMatch.value = true
                        _result.value = Result.Success(emptyList())
                    } else {
                        _noMatch.value = false
                        _result.value = Result.Success(favoritePlant)
                    }
                }
        }
    }
}