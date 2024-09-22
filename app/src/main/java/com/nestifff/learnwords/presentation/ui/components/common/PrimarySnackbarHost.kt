package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun PrimarySnackbarHost(hostState: SnackbarHostState) {
    SnackbarHost(hostState = hostState) {
        Snackbar(
            snackbarData = it,
            modifier = Modifier.padding(bottom = 16.dp),
            actionColor = AppTheme.colors.primaryLight,
            containerColor = AppTheme.colors.content,
            contentColor = AppTheme.colors.backgroundLight
        )
    }
}
