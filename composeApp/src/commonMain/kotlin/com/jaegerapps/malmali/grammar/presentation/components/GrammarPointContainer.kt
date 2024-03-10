package com.jaegerapps.malmali.grammar.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.blackBorder

@Composable
fun GrammarPointContainer(
    modifier: Modifier = Modifier,
    title: String,
    definition1: String,
    exampleKo1: String,
    exampleEn1: String,
    definition2: String? = null,
    exampleKo2: String,
    exampleEn2: String,
    background: Color = MaterialTheme.colorScheme.secondary,
    selectedBackground: Color = MaterialTheme.colorScheme.primary,
    isSelected: Boolean = false,
    isExpanded: Boolean = false,
    onExpandClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().blackBorder().clickable {
            onExpandClick()
        }.animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = title
            )
        }
        if (isExpanded) {
            Divider(Modifier.fillMaxWidth(0.6f))
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = definition1
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "예"
                    )
                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text(
                            text = exampleKo1,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = exampleEn1
                        )
                    }
                }
                definition2?.let {
                    Text(
                        text = definition1
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "예"
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = exampleKo2,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = exampleEn2
                        )
                    }
                }
            }
        }
    }
}