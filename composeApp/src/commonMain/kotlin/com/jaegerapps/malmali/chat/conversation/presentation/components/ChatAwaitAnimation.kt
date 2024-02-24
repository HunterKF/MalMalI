package com.jaegerapps.malmali.chat.conversation.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.blackBorder
import kotlinx.coroutines.delay

@Composable
fun ChatAwaitAnimation() {
    LoadingDots(
        modifier = Modifier.blackBorder().background(MaterialTheme.colorScheme.outline)
            .padding(12.dp)
    )
}

@Composable
fun LoadingDots(
    modifier: Modifier = Modifier,
    circleSize: Dp = 12.dp,
    circleColor: Color = MaterialTheme.colorScheme.surface,
    spaceBetween: Dp = 8.dp,
    travelDistance: Dp = 20.dp,
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.4f at 0 with LinearOutSlowInEasing
                        1.0f at 600 with LinearOutSlowInEasing
                        0.4f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        circleValues.forEach { value ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    /*.graphicsLayer {
                        translationY = -value * distance
                    }*/
                    .background(
                        color = circleColor.copy(
                            alpha = value
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}