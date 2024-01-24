package com.jaegerapps.malmali.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.blackBorder
import dev.icerock.moko.resources.ImageResource

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    icon: ImageResource? = null,
    vector: ImageVector? = null,
    text: String,
    leftSide: Boolean,
    size: Dp = 38.dp,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.height(80.dp).blackBorder().clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (!leftSide) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(12.dp))

        }
        if (vector != null) {
            Icon(
                modifier = Modifier.size(size).weight(1f),
                imageVector = vector,
                contentDescription = null
            )
        } else if (icon != null) {
            Icon(
                modifier = Modifier.size(size).weight(1f),
                painter = dev.icerock.moko.resources.compose.painterResource(icon),
                contentDescription = null
            )
        }
        if (leftSide) {
            Spacer(Modifier.width(12.dp))

            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
        }
    }
}