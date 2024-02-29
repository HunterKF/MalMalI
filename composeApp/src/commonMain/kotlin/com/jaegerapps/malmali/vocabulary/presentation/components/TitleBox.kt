package com.jaegerapps.malmali.vocabulary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.CustomTextFieldWithBlackBorder
import com.jaegerapps.malmali.components.blackBorder

@Composable
fun TitleBox(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .blackBorder()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier.fillMaxHeight()
                .blackBorder().background(MaterialTheme.colorScheme.primary).padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Title:",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(Modifier.width(12.dp))
        TextField(
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorContainerColor = MaterialTheme.colorScheme.error
            )
        )
    }
}