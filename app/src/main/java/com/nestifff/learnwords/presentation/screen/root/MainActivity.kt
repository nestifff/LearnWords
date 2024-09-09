package com.nestifff.learnwords.presentation.screen.root

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            ThemeProvider {
                RootScreen()
            }
        }
    }
}
