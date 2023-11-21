package com.example.pvzdatabase.ui.screen.profile

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pvzdatabase.R
import com.example.pvzdatabase.ui.component.ProfileItem
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ProfileScreen (
    modifier: Modifier = Modifier
){
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.primary
    val isDark = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = !isDark
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        ProfileItem(
            image = R.drawable.profile_picture,
            name = stringResource(R.string.my_name),
            email = stringResource(R.string.my_email)
        )
    }
}