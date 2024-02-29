package com.jaegerapps.malmali.vocabulary.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.blackBorder

@Composable
fun SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    val animatedBackgroundColor by animateColorAsState(
        if (isSelected) {
            backgroundColor
        } else {
            MaterialTheme.colorScheme.surface
        }
    )
    val animatedTextColor by animateColorAsState(
        if (isSelected) {
            MaterialTheme.colorScheme.background
        } else {
            MaterialTheme.colorScheme.onBackground
        }
    )
    Box(
        modifier = modifier.blackBorder().clickable {
            onClick()
        }.background(color = animatedBackgroundColor).padding(horizontal = 24.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,

        ) {
        Text(
            modifier = Modifier,
            text = text,
            color = animatedTextColor
        )
    }
}