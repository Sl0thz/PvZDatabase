package com.example.pvzdatabase.data.repository

import com.example.pvzdatabase.data.model.FakePlantDataSource
import com.example.pvzdatabase.data.model.FavoritePlant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PvZRepository {
    private val favoritePlant = mutableListOf<FavoritePlant>()

    init {
        if (favoritePlant.isEmpty()) {
            FakePlantDataSource.dummyPlants.forEach {
                favoritePlant.add(FavoritePlant(it))
            }
        }
    }

    fun getAllFavoritePlant(): Flow<List<FavoritePlant>> {
        return flowOf(favoritePlant)
    }

    fun getFavoritePlantById(plantId: Int): FavoritePlant {
        return favoritePlant.first {
            it.plants.id == plantId
        }
    }

    fun updateFavoritePlant(plantId: Int): Flow<Boolean> {
        val index = favoritePlant.indexOfFirst { it.plants.id == plantId }
        val result = if (index >= 0) {
            val plant = favoritePlant[index]
            plant.favorite = !plant.favorite
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getFavoritePlant(): Flow<List<FavoritePlant>> {
        return getAllFavoritePlant()
            .map {
                it.filter { plants ->
                    plants.favorite
                }
            }
    }

    fun searchPlant(query: String): Flow<List<FavoritePlant>> {
        return getAllFavoritePlant().map {
            it.filter { plants ->
                plants.plants.name.contains(query, true)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: PvZRepository? = null

        fun getInstance(): PvZRepository =
            instance ?: synchronized(this) {
                PvZRepository().apply {
                    instance = this
                }
            }
    }
}