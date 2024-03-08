package com.jaegerapps.malmali.vocabulary.presentation.study_set.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.vocabulary.presentation.study_set.StudySetUiEvent
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun VocabularyButtons(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    tint: Color = MaterialTheme.colorScheme.outline,
    onClick: (StudySetUiEvent) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {
                onClick(StudySetUiEvent.OnPrevious)
            }
        ) {
            Icon(
               Icons.Rounded.ArrowBack,
                stringResource(MR.strings.desc_previous_card),
                modifier = Modifier.size(size),
                tint = tint
            )
        }
        IconButton(
            onClick = {
                onClick(StudySetUiEvent.OnCardFlipClick)
            }
        ) {
            Icon(
               Icons.Rounded.Cached,
                stringResource(MR.strings.desc_flip_card),
                modifier = Modifier.size(size),
                tint = tint
            )
        }
        IconButton(
            onClick = {
                onClick(StudySetUiEvent.OnForward)
            }
        ) {
            Icon(
               Icons.Rounded.ArrowForward,
                stringResource(MR.strings.desc_next_card),
                modifier = Modifier.size(size),
                tint = tint
            )
        }
    }
}