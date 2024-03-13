package com.jaegerapps.malmali.practice.practicescreen.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaegerapps.malmali.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PracticeTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().height(140.dp),
        placeholder = {
            Text(stringResource(MR.strings.prompt_start_practice))
        },
        shape = RoundedCornerShape(25.dp),
        value = value,
        onValueChange = { onValueChange(it) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        maxLines = 3,
        textStyle = TextStyle(
            fontSize = 18.sp
        )
    )
}