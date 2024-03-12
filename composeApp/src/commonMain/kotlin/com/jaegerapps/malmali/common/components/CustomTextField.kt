package com.jaegerapps.malmali.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomTextFieldWithBlackBorder(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit

) {
    TextField(
        modifier = modifier.fillMaxWidth().blackBorder(),
        value = value,
        onValueChange = {
                newValue -> onValueChange(newValue)
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
    )

}