package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen

@ExperimentalMaterialApi
@Composable
fun WordsList(
    modifier: Modifier = Modifier,
    words: List<WordCollectionScreen>,
    saveClicked: (updatedWord: WordCollectionScreen) -> Unit,
    deleteWordTriggered: (id: String) -> Unit,
) {

    var selectedWordInd: Int? by remember { mutableStateOf(null) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
    ) {
        itemsIndexed(words) { ind, word ->

            val dismissState = rememberDismissState(initialValue = DismissValue.Default)

            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.White
                            else -> Color.Red
                        }
                    )
                    val alignment = Alignment.CenterEnd
                    val icon = Icons.Default.Delete

                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color)
                            .padding(end = 4.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.scale(scale)
                        )
                    }
                },
                dismissContent = {
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        deleteWordTriggered(word.id)
                    }
                    WordsListItem(
                        modifier = Modifier.padding(vertical = 4.dp),
                        word = word,
                        isSelected = selectedWordInd == ind,
                        itemOnClick = {
                            selectedWordInd = if (selectedWordInd == ind) null else ind
                        },
                        saveClicked = saveClicked
                    )
                },
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = {
                    FixedThreshold(8.dp)
                },
            )
        }
    }
}
