package com.nestifff.learnwords.presentation.screen.root

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nestifff.learnwords.app.App
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.words.domain.settings.usecase.ObserveIsDarkModeUseCase
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var observeIsDarkModeUseCase: ObserveIsDarkModeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT))
        (applicationContext as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                observeIsDarkModeUseCase.execute().collect {
                    isDarkMode = it
                }
            }

            ThemeProvider(isDarkMode = isDarkMode) {
                RootScreen()
            }
        }
    }
}
