package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen

@OptIn(ExperimentalFoundationApi::class)
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
        itemsIndexed(words, key = { _, word -> word.id }) { ind, word ->

            val currentItem by rememberUpdatedState(word)

            val dismissState2 = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        deleteWordTriggered(currentItem.id)
                        true
                    } else {
                        false
                    }
                }
            )

            SwipeToDismiss(
                state = dismissState2,
                modifier = Modifier
                    .animateItemPlacement(),
                background = { SwipeBackground(dismissState2) },
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = {
                    FractionalThreshold(0.2f)
                },
            ) {
                WordsListItem(
                    modifier = Modifier.padding(vertical = 4.dp),
                    word = word,
                    isSelected = selectedWordInd == ind,
                    itemOnClick = {
                        selectedWordInd = if (selectedWordInd == ind) null else ind
                    },
                    saveClicked = saveClicked
                )
            }

            /* val dismissState = rememberDismissState(initialValue = DismissValue.Default)
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
             )*/
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> Color.White
            else -> Color.Red
        },
        animationSpec = tween(durationMillis = 1000)
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
            .size(32.dp)
            .background(color)
            .padding(end = 12.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.scale(scale)
        )
    }
}
