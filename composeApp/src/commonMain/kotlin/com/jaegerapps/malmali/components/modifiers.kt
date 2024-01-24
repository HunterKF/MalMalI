package com.jaegerapps.malmali.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.blackBorder(clipSize: Dp = 25.dp, width: Dp = 1.dp): Modifier = composed {
    Modifier
        .clip(RoundedCornerShape(clipSize))
        .border(
        width = width,
        shape = RoundedCornerShape(clipSize),
        color = MaterialTheme.colorScheme.outline
    )
}
