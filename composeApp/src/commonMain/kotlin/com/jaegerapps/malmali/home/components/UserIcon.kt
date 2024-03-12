package com.jaegerapps.malmali.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.common.components.blackBorder
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun UserIcon(
    modifier: Modifier = Modifier,
    size: Dp = 74.dp,
    sizeBorder: Dp = 120.dp,
    icon: ImageResource,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(size),
            painter = painterResource(icon),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Box {
            Box(modifier = Modifier.size(sizeBorder).blackBorder(100.dp, 6.dp))
            Icon(
                painterResource(MR.images.icon_background),
                null,
                modifier = Modifier.size(sizeBorder),
                tint = MaterialTheme.colorScheme.background
            )
        }
    }

}