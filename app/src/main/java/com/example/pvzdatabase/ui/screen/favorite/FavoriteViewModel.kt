package com.example.pvzdatabase.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pvzdatabase.data.model.FavoritePlant
import com.example.pvzdatabase.data.repository.PvZRepository
import com.example.pvzdatabase.ui.common.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: PvZRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<List<FavoritePlant>>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    private val _noMatch = MutableStateFlow(false)
    val noMatch = _noMatch.asStateFlow()

    fun getFavoritePlant() {
        viewModelScope.launch {
            repository.getFavoritePlant()
                .catch {
                    _result.value = Result.Error(it.message.toString())
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