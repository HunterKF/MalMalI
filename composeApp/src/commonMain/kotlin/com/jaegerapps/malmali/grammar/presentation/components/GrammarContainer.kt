package com.jaegerapps.malmali.grammar.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.grammar.domain.models.GrammarLevelModel

@Composable
fun GrammarContainer(
    modifier: Modifier = Modifier,
    grammarLevelModel: GrammarLevelModel,
    isEditingMode: Boolean,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    onSelectClick: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth().animateContentSize()) {
        LevelHeader(
            title = grammarLevelModel.title,
            isEditing = isEditingMode,
            isUnlocked = grammarLevelModel.isUnlocked,
            isSelected = grammarLevelModel.isSelected,
            onExpandClick = {
                onExpandClick()
            },
            onSelectClick = {
                onSelectClick()
            }
        )
        if (isExpanded && !isEditingMode) {
            Spacer(Modifier.height(12.dp))
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                grammarLevelModel.grammarList.forEach {
                    var pointExpanded by remember {
                        mutableStateOf(false)
                    }
                    GrammarPointContainer(
                        title = it.grammarTitle,
                        definition1 = it.grammarDef1,
                        definition2 = it.grammarDef2,
                        exampleEn1 = it.exampleEng1,
                        exampleEn2 = it.exampleEng2,
                        exampleKo1 = it.exampleKor1,
                        exampleKo2 = it.exampleKor2,
                        isExpanded = pointExpanded,
                        onExpandClick = {
                            pointExpanded = !pointExpanded
                        }
                    )
                }
                if (!grammarLevelModel.isUnlocked) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        ActionButton(

                            text = "Unlock ${grammarLevelModel.title}",
                            onClick = {

                            }
                        )
                    }

                }

            }
        }
    }
}