package com.example.testprofsouz.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.testprofsouz.R
import com.example.testprofsouz.presentation.viewmodel.AllUiData
import javax.inject.Inject


class ScaffoldNavigation @Inject constructor() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var dataUi: AllUiData

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScaffoldWithTopBar() {

        var isSearching by remember {
            mutableStateOf(false)
        }
        var searchingText by remember {
            mutableStateOf("")
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxWidth()) {

                            if (!isSearching) {
                                IconButton(
                                    onClick = { dataUi.navigateTo() },
                                    modifier = Modifier.align(Alignment.CenterStart)
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.arrow_select),
                                        "backIcon",
                                        Modifier.rotate(180f)
                                    )
                                }
                                Text(
                                    text = dataUi.titleTopBar.value,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                                if (dataUi.searchAvailable.value)
                                    IconButton(
                                        onClick = { isSearching = true },
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    ) {
                                        Icon(
                                            Icons.Default.Search,
                                            "searchIcon"
                                        )
                                    }
                            } else {
                                IconButton(
                                    onClick = {
                                        dataUi.searchText.value = ""
                                        searchingText = ""
                                        isSearching = false
                                    },
                                    modifier = Modifier.align(Alignment.CenterStart)
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.arrow_select),
                                        "backIcon",
                                        Modifier.rotate(180f)
                                    )
                                }
                                TextField(
                                    value = searchingText, onValueChange = {
                                        searchingText = it
                                    }, modifier = Modifier
                                        .align(Alignment.Center)
                                        .background(Color.Transparent),
                                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                                    placeholder = {
                                        Text(text = "Поиск...")
                                    }
                                )

                                IconButton(
                                    onClick = { dataUi.search(searchingText) },
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(
                                        Icons.Default.Search,
                                        "searchIcon"
                                    )
                                }
                            }
                        }

                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = colorResource(id = R.color.first_palitre_color),
                        titleContentColor = Color.White
                    ),
                )

            }) {
            Box(modifier = Modifier.padding(it)) {
                navigator.Navigation()
            }
        }

    }
}