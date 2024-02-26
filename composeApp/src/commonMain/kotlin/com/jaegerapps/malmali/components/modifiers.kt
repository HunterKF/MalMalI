package com.jaegerapps.malmali.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

fun Modifier.blackBorder(clipSize: Dp = 25.dp, width: Dp = 1.dp, color: Color? = null): Modifier = composed {
    Modifier
        .clip(RoundedCornerShape(clipSize))
        .border(
            width = width,
            shape = RoundedCornerShape(clipSize),
            color = color ?: MaterialTheme.colorScheme.outline
        )
}

fun Modifier.verticalFadingEdge(
    scrollState: ScrollState,
    length: Dp,
    edgeColor: Color? = null
) = composed(
    debugInspectorInfo {
        name = "length"
        value = length
    }
) {
    val color = edgeColor ?: MaterialTheme.colorScheme.surface

    drawWithContent {
        val lengthValue = length.toPx()
        val scrollFromTop = scrollState.value
        val scrollFromBottom = scrollState.maxValue - scrollState.value

        val topFadingEdgeStrength = lengthValue * (scrollFromTop / lengthValue).coerceAtMost(1f)

        val bottomFadingEdgeStrength = lengthValue * (scrollFromBottom / lengthValue).coerceAtMost(1f)

        drawContent()

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    color,
                    Color.Transparent,
                ),
                startY = 0f,
                endY = topFadingEdgeStrength,
            ),
            size = Size(
                this.size.width,
                topFadingEdgeStrength
            ),
        )

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    color,
                ),
                startY = size.height - bottomFadingEdgeStrength,
                endY = size.height,
            ),
            topLeft = Offset(x = 0f, y = size.height - bottomFadingEdgeStrength),
        )
    }
}