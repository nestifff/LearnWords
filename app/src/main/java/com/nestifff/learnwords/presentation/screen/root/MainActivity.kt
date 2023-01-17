package com.nestifff.learnwords.presentation.screen.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nestifff.learnwords.presentation.ui.theme.ThemeCommon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThemeCommon {
                RootScreen()
            }
        }
    }
}
