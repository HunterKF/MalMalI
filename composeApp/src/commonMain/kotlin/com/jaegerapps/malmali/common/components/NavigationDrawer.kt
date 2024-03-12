package com.jaegerapps.malmali.common.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.common.models.Routes
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun CustomNavigationDrawer(
    drawerState: DrawerState,
    iconSize: Dp = 28.dp,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    val animateOffset by animateDpAsState(
        if (drawerState.isClosed) {
            ((-24).dp)
        } else {
            0.dp
        }
    )
    ModalNavigationDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        drawerContent = {
            Box(
                Modifier.fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 1f))
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp))
                    .fillMaxWidth(0.7f)
                    .background(MaterialTheme.colorScheme.background)
                    .offset(x = animateOffset),
                contentAlignment = Alignment.TopStart

            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Spacer(Modifier.height(18.dp))
                    DrawerHeader(
                        modifier = Modifier.padding(12.dp),
                        icon = MR.images.cat_icon
                    )
                    Spacer(Modifier.height(18.dp))
                    Column(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Divider()
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    Icons.Rounded.Home,
                                    stringResource(MR.strings.desc_home),
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(iconSize)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MR.strings.title_home)
                                )
                            },
                            selected = false,
                            onClick = { onNavigate(Routes.HOME) }
                        )
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    painterResource(MR.images.icon_pencil),
                                    stringResource(MR.strings.desc_practice),
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(iconSize)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MR.strings.title_practice)
                                )
                            },
                            selected = false,
                            onClick = { onNavigate(Routes.PRACTICE) }
                        )
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    Icons.Rounded.Message,
                                    stringResource(MR.strings.desc_chat),
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(iconSize)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MR.strings.title_chat)
                                )
                            },
                            selected = false,
                            onClick = { onNavigate(Routes.CHAT) }
                        )
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    painterResource(MR.images.icon_grammar),
                                    stringResource(MR.strings.desc_grammar),
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(iconSize)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MR.strings.title_grammar)
                                )
                            },
                            selected = false,
                            onClick = { onNavigate(Routes.GRAMMAR) }
                        )
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    painterResource(MR.images.icon_flashcards),
                                    stringResource(MR.strings.desc_vocabulary),
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(iconSize)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MR.strings.title_vocabulary)
                                )
                            },
                            selected = false,
                            onClick = { onNavigate(Routes.VOCABULARY) }
                        )
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    Icons.Rounded.Person,
                                    stringResource(MR.strings.desc_profile),
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(iconSize)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MR.strings.title_profile)
                                )
                            },
                            selected = false,
                            onClick = { onNavigate(Routes.PROFILE) }
                        )
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    Icons.Rounded.Info,
                                    stringResource(MR.strings.desc_about),
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(iconSize)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MR.strings.title_about)
                                )
                            },
                            selected = false,
                            onClick = { onNavigate(Routes.ABOUT) }
                        )
                    }
                }

            }

        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}


@Composable
fun DrawerHeader(
    icon: ImageResource,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.blackBorder(clipSize = 100.dp).size(size),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(icon),
                null,
                modifier = Modifier.padding(8.dp),
            )
        }

    }
}
