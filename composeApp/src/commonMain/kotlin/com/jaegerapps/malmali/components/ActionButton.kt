package com.jaegerapps.malmali.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    disabledColor: Color = Color(0xffd3d3d3),
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    isEnabled: Boolean = true,
    border: BorderStroke? = null,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        border = border,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = disabledColor,
            contentColor = contentColor
        ),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
        )
    }
}