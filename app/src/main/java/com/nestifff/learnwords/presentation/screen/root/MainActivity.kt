package com.nestifff.learnwords.presentation.screen.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ThemeProvider {
                RootScreen()
            }
        }
    }
}
