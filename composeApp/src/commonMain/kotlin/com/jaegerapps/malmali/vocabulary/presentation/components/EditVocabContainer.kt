package com.jaegerapps.malmali.vocabulary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.common.components.blackBorder

@Composable
fun EditVocabContainer(
    modifier: Modifier = Modifier,
    word: String = "",
    def: String = "",
    isError: Boolean = false,
    onWordChange: (String) -> Unit,
    onDefChange: (String) -> Unit,
    onDelete: () -> Unit,
    onClearError: () -> Unit
) {

    LaunchedEffect(key1 = word, key2 = def) {
        if (isError && word.isNotBlank() && def.isNotBlank()) {
            onClearError()
        }
    }
    Row(
        modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min).blackBorder(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = word,
            placeholder = {

                Text("word", textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
            },
            singleLine = true,
            isError = isError,
            onValueChange = { newValue -> onWordChange(newValue) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorContainerColor = MaterialTheme.colorScheme.error
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start)

        )
        Spacer(Modifier.fillMaxHeight().width(1.dp).background(MaterialTheme.colorScheme.outline))

        TextField(
            modifier = Modifier.weight(1f),
            value = def,
            placeholder = {
                Text("def", textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
            },
            singleLine = true,
            isError = isError,
            onValueChange = { newValue -> onDefChange(newValue) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorContainerColor = MaterialTheme.colorScheme.error

            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start)

        )
        Spacer(Modifier.fillMaxHeight().width(1.dp).background(MaterialTheme.colorScheme.outline))

        IconButton(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onClick = {
                onDelete()
            }
        ) {
            Icon(
                Icons.Outlined.DeleteOutline,
                "Delete word"
            )
        }
    }
}