package com.jaegerapps.malmali.vocabulary.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.IconContainer
import com.jaegerapps.malmali.components.blackBorder

@Composable
fun FolderContainer(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter,
    expanded: Boolean,
    onEvent: () -> Unit,
    buttonContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .blackBorder()
            .background(MaterialTheme.colorScheme.primary)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable { onEvent() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconContainer(
                icon = icon
            )
            Spacer(Modifier.width(8.dp))
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = title,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        AnimatedVisibility(expanded) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                buttonContent()
            }
        }

    }
}