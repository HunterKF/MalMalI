package com.jaegerapps.malmali.vocabulary.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.common.components.IconContainer
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SelectIcon(
    modifier: Modifier = Modifier,
    defaultIcon: Painter,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(MR.strings.prompt_select_icon)
        )
        Spacer(Modifier.width(24.dp))
        IconContainer(
            size = 50.dp,
            modifier = Modifier.clip(
                CircleShape).clickable { onClick() },
            icon = defaultIcon
        )
    }
}