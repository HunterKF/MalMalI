package com.jaegerapps.malmali.vocabulary.study_flashcards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.blackBorder
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabularyCard
import com.jaegerapps.malmali.vocabulary.study_flashcards.StudyFlashcardsUiEvent
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun VocabularyContainer(
    modifier: Modifier = Modifier,
    setSize: Int,
    currentIndex: Int,
    card: VocabularyCard,
    showBack: Boolean,
    onClick: (StudyFlashcardsUiEvent) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxHeight(0.4f).blackBorder(20.dp).padding(0.dp)
    ) {
        Column {


            Box(
                modifier = Modifier.fillMaxWidth().weight(1f).clickable {
                    onClick(StudyFlashcardsUiEvent.OnCardFlipClick)
                }, contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart).padding(start = 12.dp, top = 8.dp),
                    text = "$currentIndex / $setSize"
                )
                Text(
                    text = if (!showBack) card.word else card.definition,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = if (showBack) stringResource(MR.strings.placeholder_definition) else stringResource(MR.strings.placeholder_word)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                    )
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                        .clickable { onClick(StudyFlashcardsUiEvent.OnGotItClick) }
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Got it",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                        .clickable { onClick(StudyFlashcardsUiEvent.OnDontKnowClick) }
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = "Don't know",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }


    }

}