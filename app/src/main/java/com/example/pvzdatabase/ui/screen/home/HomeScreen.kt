package com.example.pvzdatabase.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pvzdatabase.R
import com.example.pvzdatabase.data.di.Injection
import com.example.pvzdatabase.data.model.FavoritePlant
import com.example.pvzdatabase.ui.common.Result
import com.example.pvzdatabase.ui.common.ViewModelFactory
import com.example.pvzdatabase.ui.component.FavoriteItem
import com.example.pvzdatabase.ui.component.SearchBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.primary
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = false
        )
    }

    viewModel.result.collectAsState(initial = Result.Loading).value.let { result ->
        when (result) {
            is Result.Loading -> {
                viewModel.getAllPlants()
            }
            is Result.Success -> {
                HomeContent(
                    modifier = modifier,
                    favoritePlant = result.data,
                    query = viewModel.query.value,
                    noMatch = viewModel.noMatch.value,
                    navigateToDetail = navigateToDetail,
                    searchPlant = viewModel::searchPlant,
                )
            }
            is Result.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    favoritePlant: List<FavoritePlant>,
    query: String,
    noMatch: Boolean,
    navigateToDetail: (Int) -> Unit,
    searchPlant: (String) -> Unit,
) {
    Column {
        SearchBar(
            query = query,
            onQueryChange = {
                searchPlant(it)
            },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
        )
        if (noMatch) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(R.string.no_data))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ) {
                items(
                    items = favoritePlant,
                    key = { it.plants.id }
                ) { data ->
                    FavoriteItem(
                        image = data.plants.photoUrl,
                        name = data.plants.name,
                        modifier = Modifier
                            .clickable {
                                navigateToDetail(data.plants.id)
                            }
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
    }
}