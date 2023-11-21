package com.example.pvzdatabase.data.di

import com.example.pvzdatabase.data.repository.PvZRepository

object Injection {
    fun provideRepository(): PvZRepository {
        return PvZRepository.getInstance()
    }
}