package com.jaegerapps.malmali.home.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.common.components.blackBorder

@Composable
fun LevelBar(
    modifier: Modifier = Modifier,
    startLevel: Int,
    endLevel: Int,
    experience: Int,
    gaugeLocation: Float,
    achievedColor: Color = MaterialTheme.colorScheme.secondary,
    gaugeColor: Color = MaterialTheme.colorScheme.primary
) {

    var animate by remember {
        mutableStateOf(false)
    }
    val animatedGauge by animateFloatAsState(
        if (animate) {
            gaugeLocation
        } else {
            0f
        },
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )
    LaunchedEffect(Unit) {
        animate = true
    }
    Row(
        modifier = modifier.blackBorder().padding(vertical = 8.dp, horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = startLevel.toString(),
            fontWeight = FontWeight.Bold
        )
        Box(Modifier.padding(horizontal = 12.dp).weight(1f),
            contentAlignment = Alignment.Center) {
            //background achievement bar
            Box(
                modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth().height(12.dp).blackBorder().background(gaugeColor),
            )
            //foreground achievement bar
            Box(
                modifier = Modifier.align(Alignment.CenterStart).padding(horizontal = 0.dp).fillMaxWidth(animatedGauge).height(12.dp).blackBorder().background(achievedColor),
            )
            //circular progress indicator
            Box(
                modifier = Modifier.align(Alignment.CenterStart).fillMaxWidth(animatedGauge),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier.size(25.dp).blackBorder(100.dp).background(achievedColor),
                    contentAlignment = Alignment.Center
                ) {
                    Box(Modifier.size(15.dp).clip(CircleShape).background(gaugeColor))
                }
            }
        }
        Text(
            text = endLevel.toString(),
            fontWeight = FontWeight.Bold
        )
    }
}