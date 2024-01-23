package com.jaegerapps.malmali

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.components.TopBarLogo
import com.jaegerapps.malmali.components.CustomTextFieldWithBlackBorder
import com.jaegerapps.malmali.components.IconContainer
import com.jaegerapps.malmali.components.SettingsAndModal
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.vocabulary.domain.VocabSet
import com.jaegerapps.malmali.vocabulary.components.AddCardButton
import com.jaegerapps.malmali.vocabulary.components.EditVocabContainer
import com.jaegerapps.malmali.vocabulary.components.FolderContainer
import com.jaegerapps.malmali.vocabulary.components.SelectIcon
import com.jaegerapps.malmali.vocabulary.components.SelectableButton
import com.jaegerapps.malmali.vocabulary.components.TitleBox
import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.domain.UiFlashcard
import com.jaegerapps.malmali.vocabulary.study_flashcards.components.VocabularyButtons
import com.jaegerapps.malmali.vocabulary.study_flashcards.components.VocabularyContainer
import core.presentation.MalMalITheme
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@Preview
@Composable
private fun Preview_TopBarLogo() {
    MalMalITheme(false) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface
        ) { paddingValues ->
            TopBarLogo {
                Text(
                    "단어",
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.padding(paddingValues))
        }
    }
}

@Preview
@Composable
private fun Preview_IconContainer() {
    MalMalITheme(false) {
        IconContainer(
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.cat_icon)
        )
    }
}

@Preview
@Composable
private fun Preview_FolderContainer() {
    MalMalITheme(false) {
        var expanded by remember {
            mutableStateOf(
                false
            )
        }
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            FolderContainer(
                title = "Testing",
                icon = painterResource(MR.images.cat_icon),
                expanded = expanded,
                onEvent = {
                    expanded = !expanded
                }
            ) {
                Text("Content")
            }
            Spacer(Modifier.height(20.dp))
            FolderContainer(
                title = "Testing",
                icon = painterResource(MR.images.cat_icon),
                expanded = !expanded,
                onEvent = {
                    expanded = !expanded

                }
            ) {
                Text("Content")
            }
        }

    }
}

@Preview
@Composable
private fun Preview_EditVocabContainer() {
    MalMalITheme(false) {
        Column(Modifier.padding(12.dp)) {
            var word by remember {
                mutableStateOf("")
            }
            var def by remember {
                mutableStateOf("")
            }
            EditVocabContainer(
                word = word,
                def = def,
                onWordChange = { newWord -> word = newWord },
                onDefChange = { newDef -> def = newDef },
                onDelete = {},
                onClearError = {}
            )
            Spacer(Modifier.height(12.dp))
            EditVocabContainer(
                word = word,
                def = def,
                isError = true,
                onWordChange = { newWord -> word = newWord },
                onDefChange = { newDef -> def = newDef },
                onDelete = {},
                onClearError = {}
            )
            Spacer(Modifier.height(12.dp))

            EditVocabContainer(
                word = word,
                def = def,
                onWordChange = { newWord -> word = newWord },
                onDefChange = { newDef -> def = newDef },
                onDelete = {},
                onClearError = {}
            )
        }

    }
}

@Preview
@Composable
private fun Preview_CustomTextFieldWithBlackBorder() {
    MalMalITheme(false) {
        var value by remember {
            mutableStateOf("")
        }
        Column {
            CustomTextFieldWithBlackBorder(
                value = value,
                onValueChange = {
                    value = it
                }
            )
            Spacer(Modifier.height(12.dp))
            CustomTextFieldWithBlackBorder(
                value = value,
                onValueChange = {
                    value = it
                }
            )
        }

    }

}

@Preview
@Composable
private fun Preview_TitleBox() {
    MalMalITheme(false) {
        var value by remember {
            mutableStateOf("")
        }
        Column(Modifier.padding(12.dp)) {
            TitleBox(
                value, onValueChange = { value = it },
                isError = false
            )
            TitleBox(
                value, onValueChange = { value = it },
                isError = true
            )
        }

    }

}

@Preview
@Composable
private fun Preview_SelectableButton() {
    MalMalITheme(false) {
        var value by remember {
            mutableStateOf(false)
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SelectableButton(
                text = "Private",
                isSelected = value,
                onClick = { value = !value }
            )
            SelectableButton(
                text = "Public",
                isSelected = !value,
                onClick = { value = !value }
            )
        }
    }

}

@Preview
@Composable
private fun Preview_SelectIcon() {
    MalMalITheme(false) {
        Column(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SelectIcon(
                defaultIcon = painterResource(MR.images.cat_icon),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun Preview_AddCardButton() {
    MalMalITheme(false) {
        AddCardButton(
            text = stringResource(MR.strings.prompt_add_card),
            onClick = {},
            onLongClick = {}

        )
    }

}

@Preview
@Composable
private fun Preview_ActionButton() {
    MalMalITheme(false) {
        Column(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ActionButton(
                text = stringResource(MR.strings.prompt_save),
                onClick = {}
            )
            ActionButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(MR.strings.prompt_save),
                onClick = {}
            )
            ActionButton(
                text = stringResource(MR.strings.prompt_save),
                isEnabled = false,
                onClick = {}
            )
            ActionButton(
                text = stringResource(MR.strings.prompt_save),
                modifier = Modifier,
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                onClick = {}
            )
            ActionButton(
                text = stringResource(MR.strings.prompt_save),
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun Preview_FolderScreen() {
    val list = listOf<VocabSet>(
        VocabSet(
            "Default",
            MR.images.cat_icon,
            expanded = false,
            setId = 1,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Food",
            MR.images.cat_icon,
            expanded = false,
            setId = 2,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Travel",
            MR.images.cat_icon,
            expanded = false,
            setId = 3,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
        VocabSet(
            "Test",
            MR.images.cat_icon,
            expanded = false,
            setId = 4,
            isPrivate = SetMode.PUBLIC,
            dateCreated = 1
        ),
    )
    MalMalITheme(false) {
//        FolderScreen(
//            list
//        )
    }
}

@Preview
@Composable
private fun Preview_SettingsAndModal() {
    MalMalITheme(false) {
        Column(Modifier.padding(12.dp)) {
            SettingsAndModal(
                onSettingsClick = {},
                onModalClick = {}
            )
        }
    }
}

@Preview
@Composable
fun Preview_ModalScreen() {
    MalMalITheme(false) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val scope = rememberCoroutineScope()
        CustomNavigationDrawer(drawerState = drawerState, onNavigate = { newValue -> }) {
            Button(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Text("Open drawer")
            }
        }
    }
}

@Preview
@Composable
fun Preview_VocabularyContainer() {
    MalMalITheme(false) {
        VocabularyContainer(
            setSize = 30,
            currentIndex = 1,
            card = UiFlashcard(uiId = 1, word = "먹다", def = "to eat", level = 1, error = false),
            showBack = false,
            onClick = {

            }
        )
    }

}

@Preview
@Composable
fun Preview_VocabularyButtons() {
    MalMalITheme(false) {
        VocabularyButtons(
            onClick = {

            }
        )
    }
}
