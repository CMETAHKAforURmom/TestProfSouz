package com.example.testprofsouz.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testprofsouz.presentation.ui.screens.DownTimeScreen
import com.example.testprofsouz.presentation.ui.screens.DropDownCascade
import com.example.testprofsouz.presentation.viewmodel.AllUiData
import com.example.testprofsouz.presentation.viewmodel.MainScreenViewModel
import javax.inject.Inject


class Navigator @Inject constructor(private val dataUi: AllUiData){

    @Inject
    lateinit var mainScreenViewModel: MainScreenViewModel

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        dataUi.setScreen(navController)
        NavHost(
            navController = navController,
            startDestination = NavigationGraph.DownTimeScreen.name
        ) {
            composable(NavigationGraph.DownTimeScreen.name) {
                DownTimeScreen(mainScreenViewModel)
            }
            composable(NavigationGraph.CascadeDropScreen.name) {
                DropDownCascade(dataUi.currentList, mainScreenViewModel, dataUi.searchText)
            }
        }
    }
}

enum class NavigationGraph{
    DownTimeScreen, CascadeDropScreen
}