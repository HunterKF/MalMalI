package com.jaegerapps.malmali.practice.practice.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.common.components.blackBorder
import com.jaegerapps.malmali.common.models.GrammarPointModel
import com.jaegerapps.malmali.common.models.VocabularyCardModel
import com.jaegerapps.malmali.practice.practice.presentation.PracticeUiEvent
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PracticeContainer(
    modifier: Modifier = Modifier,
    vocab: VocabularyCardModel,
    grammar: GrammarPointModel,
    vocabExpanded: Boolean,
    grammarExpanded: Boolean,
    onClick: (PracticeUiEvent) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().blackBorder().padding(horizontal = 12.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContainerDropClickable(
                title = vocab.word,
                isExpanded = vocabExpanded,
                onClick = { onClick(PracticeUiEvent.ToggleVocabDropDown) })
            IconButton(
                onClick = {
                    onClick(PracticeUiEvent.RefreshPracticeContainer)
                }
            ) {
                Icon(imageVector = Icons.Default.Refresh, null)
            }
            ContainerDropClickable(
                title = grammar.grammarTitle,
                isExpanded = grammarExpanded,
                onClick = { onClick(PracticeUiEvent.ToggleGrammarDropDown) })
        }
        AnimatedVisibility(vocabExpanded || grammarExpanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = when {
                        vocabExpanded -> stringResource(
                            MR.strings.practice_vocabulary_definition,
                            vocab.definition
                        )

                        grammarExpanded -> stringResource(
                            MR.strings.practice_definition_1,
                            grammar.grammarDef1
                        )

                        else -> ""
                    }
                )
                if (grammarExpanded) {
                    grammar.grammarDef2?.let {
                        Text(
                            text = stringResource(MR.strings.practice_definition_1, it)
                        )
                    }
                }
                Box(
                    modifier = Modifier.padding(6.dp).fillMaxWidth(0.5f).clip(RoundedCornerShape(25.dp)).clickable { onClick(
                        PracticeUiEvent.CloseDropDowns) },
                    contentAlignment = Alignment.Center,

                ) {
                    Icon(
                        Icons.Outlined.ArrowDropUp,
                        null
                    )
                }
            }
        }
    }
}

