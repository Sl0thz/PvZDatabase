package com.example.pvzdatabase.ui.screen.detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pvzdatabase.data.di.Injection
import com.example.pvzdatabase.ui.common.Result
import com.example.pvzdatabase.ui.common.ViewModelFactory
import com.example.pvzdatabase.ui.component.ItemDetail
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    plantId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
    navigateToFavorite: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.background
    val isDark = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = !isDark
        )
    }

    viewModel.result.collectAsState(initial = Result.Loading).value.let { result ->
        when (result) {
            is Result.Loading -> {
                viewModel.getFavoritePlantById(plantId)
            }
            is Result.Success -> {
                val favoritePlant = result.data
                val plant = favoritePlant.plants
                DetailContent(
                    modifier = modifier,
                    id = plant.id,
                    name = plant.name,
                    description = plant.description,
                    cost = plant.cost,
                    recharge = plant.recharge,
                    damage = plant.damage,
                    toughness = plant.toughness,
                    range = plant.range,
                    image = plant.photoUrl,
                    highlight = favoritePlant.favorite,
                    onBackClick = navigateBack,
                    onUpdateFavorite = {
                        viewModel.updateFavorite(plantId)
                        navigateToFavorite()
                    }
                )
            }
            is Result.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    id: Int,
    name: String,
    description: String,
    cost: String,
    recharge: String,
    damage: String,
    toughness: String,
    range: String,
    image: Int,
    highlight: Boolean,
    onBackClick: () -> Unit,
    onUpdateFavorite: (Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        ItemDetail(
            id = id,
            name = name,
            description = description,
            cost = cost,
            recharge = recharge,
            damage = damage,
            toughness = toughness,
            range = range,
            image = image,
            highlight = highlight,
            onBackClick = onBackClick,
            onUpdateFavorite = onUpdateFavorite
        )
    }
}