
package com.example.pvzdatabase.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pvzdatabase.R
import com.example.pvzdatabase.ui.navigation.NavigationItem
import com.example.pvzdatabase.ui.navigation.Screen
import com.example.pvzdatabase.ui.screen.detail.DetailScreen
import com.example.pvzdatabase.ui.screen.favorite.FavoriteScreen
import com.example.pvzdatabase.ui.screen.home.HomeScreen
import com.example.pvzdatabase.ui.screen.profile.ProfileScreen

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier,
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { plantId ->
                        navController.navigate(Screen.Detail.createRoute(plantId))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("plantId") { type = NavType.IntType })
            ){
                DetailScreen(
                    plantId = it.arguments?.getInt("plantId") ?: 0,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToFavorite ={
                        navController.popBackStack()
                        navController.navigate(Screen.Favorite.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Favorite.route){
                FavoriteScreen(navigateToDetail = { plantId ->
                    navController.navigate(Screen.Detail.createRoute(plantId))
                },
                    )
            }
        }
    }
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.profile),
                icon = Icons.Default.Person,
                screen = Screen.Profile
            ),
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            navigationItems.map {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title
                        )
                    },
                    label = { Text(it.title) },
                    selected = currentRoute == it.screen.route,
                    onClick = {
                        navController.navigate(it.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.tertiary,
                        selectedTextColor = MaterialTheme.colorScheme.background,
                        indicatorColor = MaterialTheme.colorScheme.background,
                        unselectedIconColor = MaterialTheme.colorScheme.background,
                        unselectedTextColor = MaterialTheme.colorScheme.background,
                    ),
                    alwaysShowLabel = false,
                )
            }
        }
    }
}