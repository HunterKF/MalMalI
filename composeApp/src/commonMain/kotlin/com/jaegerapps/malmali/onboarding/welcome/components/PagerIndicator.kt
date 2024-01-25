package com.jaegerapps.malmali.onboarding.welcome.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    numberOfPages: Int,
) {
    Row(
        modifier = modifier.height(30.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..numberOfPages) {
            val animatedDp by animateDpAsState(
                if (i == currentPage) {
                    24.dp
                } else {
                    14.dp
                }
            )
            CircleIndicator(animatedDp)
        }
    }

}

@Composable
private fun CircleIndicator(
    size: Dp,
) {
    Box(modifier = Modifier.size(size).clip(CircleShape).background(MaterialTheme.colorScheme.outline))
}