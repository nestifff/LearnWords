package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionLearnButton
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionTopBar
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionsSwitcher
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun CollectionScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WordsTheme.colors.background)
    ) {
        Column {
            CollectionTopBar()
            CollectionsSwitcher(collections = listOf("In progress", "Learned", "Favorites"))
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            CollectionLearnButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp, bottom = 32.dp)
            )

        }
    }
}
