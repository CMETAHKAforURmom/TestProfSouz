package com.example.testprofsouz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.testprofsouz.presentation.theme.TestProfSouzTheme
import com.example.testprofsouz.presentation.ui.ScaffoldNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var scaffoldNavigation: ScaffoldNavigation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestProfSouzTheme {
                scaffoldNavigation.ScaffoldWithTopBar()
            }
        }
    }
}
