package com.jaegerapps.malmali.practice.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.blackBorder
import com.jaegerapps.malmali.practice.models.UiPracticeGrammar
import com.jaegerapps.malmali.practice.models.UiPracticeVocab
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PracticeContainer(
    modifier: Modifier = Modifier,
    vocab: UiPracticeVocab,
    grammar: UiPracticeGrammar,
    vocabExpanded: Boolean,
    grammarExpanded: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().blackBorder().padding(12.dp).animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContainerDropClickable(
                title = vocab.word,
                isExpanded = vocabExpanded,
                onClick = { onClick() })
            IconButton(
                onClick = {
                    onClick()
                }
            ) {
                Icon(imageVector = Icons.Default.Refresh, null)
            }
            ContainerDropClickable(
                title = grammar.grammar,
                isExpanded = grammarExpanded,
                onClick = { onClick() })
        }
        AnimatedVisibility(vocabExpanded || grammarExpanded) {
            Column(modifier = Modifier.clickable { onClick() }) {

                Text(
                    text = when {
                        vocabExpanded -> stringResource(
                            MR.strings.practice_vocabulary_definition,
                            vocab.definition
                        )

                        grammarExpanded -> stringResource(
                            MR.strings.practice_definition_1,
                            grammar.definition1
                        )
                        else -> ""
                    }
                )
                if (grammarExpanded) {
                    grammar.definition2?.let {
                        Text(
                            text = stringResource(MR.strings.practice_definition_1, it)
                        )
                    }
                }
            }
        }
    }
}

