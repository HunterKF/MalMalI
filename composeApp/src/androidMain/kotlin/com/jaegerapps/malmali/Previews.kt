package com.jaegerapps.malmali

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.jaegerapps.malmali.chat.conversation.presentation.ConversationScreen
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatAwaitAnimation
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatBubble
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatPopUpAlert
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatTextField
import com.jaegerapps.malmali.chat.models.ConversationUi
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.components.TopBarLogo
import com.jaegerapps.malmali.components.CustomTextFieldWithBlackBorder
import com.jaegerapps.malmali.components.IconContainer
import com.jaegerapps.malmali.components.SettingsAndModal
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.grammar.presentation.components.GrammarContainer
import com.jaegerapps.malmali.grammar.presentation.components.GrammarLevelContainer
import com.jaegerapps.malmali.grammar.presentation.components.GrammarListContainer
import com.jaegerapps.malmali.grammar.presentation.components.GrammarPointContainer
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint
import com.jaegerapps.malmali.home.components.CardButton
import com.jaegerapps.malmali.home.components.LevelBar
import com.jaegerapps.malmali.home.components.UserIcon
import com.jaegerapps.malmali.onboarding.intro.components.OnboardingContainer
import com.jaegerapps.malmali.onboarding.intro.components.PagerIndicator
import com.jaegerapps.malmali.onboarding.intro.components.SkipAndNextButton
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
import core.Knower
import core.Knower.d
import core.presentation.MalMalITheme
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

private val exampleGrammarPoint = GrammarPoint(
    grammarCategory = 1,
    grammarTitle = "Title 2",
    grammarDef1 = "Definition 2-1",
    grammarDef2 = "Definition 2-2",
    exampleEn1 = "English Example 2-1",
    exampleEn2 = "English Example 2-2",
    exampleKo1 = "Korean Example 2-1",
    exampleKo2 = "Korean Example 2-2"
)

private val exampleGrammarPointList = (1..10).map {
    GrammarPoint(
        grammarCategory = 1,
        grammarTitle = "Point $it",
        grammarDef1 = "Definition $it-1",
        grammarDef2 = "Definition $it-2",
        exampleEn1 = "English Example $it-1",
        exampleEn2 = "English Example $it-2",
        exampleKo1 = "Korean Example $it-1",
        exampleKo2 = "Korean Example $it-2"
    )
}

private val exampleGrammarLevel = GrammarLevel(
    title = "Level 1",
    isUnlocked = false,
    exampleGrammarPointList
)
private val exampleGrammarLevelList = listOf(
    GrammarLevel(
        title = "Level 1",
        isUnlocked = true,
        exampleGrammarPointList
    ),
    GrammarLevel(
        title = "Level 2",
        isUnlocked = false,
        exampleGrammarPointList
    ),
    GrammarLevel(
        title = "Level 3",
        isUnlocked = false,
        exampleGrammarPointList
    ),
)

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
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                Column {
                    IconContainer(
                        icon = painterResource(MR.images.user_icon_bear_1)
                    )
                    Text("1")
                }
                Column {
                    IconContainer(
                        icon = painterResource(MR.images.user_icon_bear_2),
                        backgroundColor = MaterialTheme.colorScheme.primary
                    )
                    Text(" 2")
                }
                Column {
                    IconContainer(
                        icon = painterResource(MR.images.user_icon_bear_3)
                    )
                    Text("3")
                }
            }
            Spacer(Modifier.height(24.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 4),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) {
                    val bear = IconResource.resourceFromTag("bear $it").resource
                    IconContainer(
                        modifier = Modifier.aspectRatio(1f),
                        icon = painterResource(bear)
                    )
                }


            }
        }

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

@Preview
@Composable
fun Preview_LevelBar() {
    MalMalITheme(false) {
        Column {
            LevelBar(
                startLevel = 1,
                endLevel = 2,
                experience = 50,
                gaugeLocation = 0.5f
            )
            Spacer(Modifier.height(12.dp))
            LevelBar(
                startLevel = 1,
                endLevel = 2,
                experience = 50,
                gaugeLocation = 0.9f
            )
        }

    }
}

@Preview
@Composable
fun Preview_CardButton() {
    Column {

        Row(Modifier.fillMaxWidth().padding(12.dp)) {
            CardButton(
                modifier = Modifier.weight(1f),
                icon = MR.images.icon_grammar,
                text = "이\n야\n기",
                leftSide = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.weight(0.5f))
            CardButton(
                modifier = Modifier.weight(1f),
                icon = MR.images.icon_pencil,
                text = "연\n습",
                leftSide = false,
                onClick = {}
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(Modifier.fillMaxWidth().padding(12.dp)) {
            CardButton(
                modifier = Modifier.weight(1f),
                icon = MR.images.icon_grammar,
                text = "이\n야\n기",
                leftSide = true,
                onClick = {}
            )
            Spacer(modifier = Modifier.weight(0.5f))
            CardButton(
                modifier = Modifier.weight(1f),
                icon = MR.images.icon_pencil,
                text = "연\n습",
                leftSide = false,
                onClick = {}
            )

        }
    }

}

@Preview
@Composable
fun Preview_UserIcon() {
    MalMalITheme(false) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            UserIcon(
                icon = MR.images.cat_icon,
            )
        }
    }
}


@Preview
@Composable
fun Preview_GrammarLevelContainer() {
    MalMalITheme(false) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            GrammarLevelContainer(
                title = "Topik 1",
                isUnlocked = false,
                onExpandClick = {

                },
                onSelectClick = {}
            )
            GrammarLevelContainer(
                title = "Topik 2",
                isUnlocked = true,
                onExpandClick = {

                },
                onSelectClick = {}
            )
            GrammarLevelContainer(
                title = "Topik 3",
                isUnlocked = true,
                isEditing = true,
                isSelected = true,
                onExpandClick = {

                },
                onSelectClick = {}
            )
            GrammarLevelContainer(
                title = "Topik 4",
                isUnlocked = true,
                isSelected = false,
                onExpandClick = {

                },
                onSelectClick = {}
            )
        }

    }
}

@Preview
@Composable
fun Preview_GrammarPointContainer() {
    MalMalITheme(false) {
        var expanded by remember {
            mutableStateOf(false)
        }
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            item {
                GrammarPointContainer(
                    title = "Sample Title",
                    definition1 = "Definition 1",
                    exampleKo1 = "Korean Example 1",
                    exampleEn1 = "English Example 1",
                    exampleKo2 = "Korean Example 2",
                    exampleEn2 = "English Example 2",
                    isUnlocked = true,
                    isEditing = false,
                    isSelected = false,
                    isExpanded = expanded,
                    onExpandClick = {
                        expanded = !expanded
                    },
                    onSelectClick = {}
                )
                Spacer(Modifier.height(18.dp))
                GrammarPointContainer(
                    title = "Sample Title",
                    definition1 = "Definition 1",
                    exampleKo1 = "Korean Example 1",
                    exampleEn1 = "English Example 1",
                    exampleKo2 = "Korean Example 2",
                    exampleEn2 = "English Example 2",
                    isUnlocked = true,
                    isEditing = false,
                    isSelected = false,
                    isExpanded = true,
                    onExpandClick = {},
                    onSelectClick = {}
                )
                Spacer(Modifier.height(18.dp))

                GrammarPointContainer(
                    title = "Sample Title",
                    definition1 = "Definition 1",
                    exampleKo1 = "Korean Example 1",
                    exampleEn1 = "English Example 1",
                    exampleKo2 = "Korean Example 2",
                    exampleEn2 = "English Example 2",
                    isUnlocked = true,
                    isEditing = true,
                    isSelected = false,
                    isExpanded = true,
                    onExpandClick = {},
                    onSelectClick = {}
                )
                Spacer(Modifier.height(18.dp))

                GrammarPointContainer(
                    title = "Sample Title",
                    definition1 = "Definition 1",
                    exampleKo1 = "Korean Example 1",
                    exampleEn1 = "English Example 1",
                    exampleKo2 = "Korean Example 2",
                    exampleEn2 = "English Example 2",
                    isUnlocked = true,
                    isEditing = true,
                    isSelected = false,
                    isExpanded = true,
                    onExpandClick = {},
                    onSelectClick = {}
                )
                Spacer(Modifier.height(18.dp))

            }

        }

    }
}

@Preview
@Composable
fun Preview_GrammarContainer() {
    // Sample data for preview
    val topik1 = listOf(
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 1",
            grammarDef1 = "Definition 1-1",
            grammarDef2 = "Definition 1-2",
            exampleEn1 = "English Example 1-1",
            exampleEn2 = "English Example 1-2",
            exampleKo1 = "Korean Example 1-1",
            exampleKo2 = "Korean Example 1-2"
        ),
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEn1 = "English Example 2-1",
            exampleEn2 = "English Example 2-2",
            exampleKo1 = "Korean Example 2-1",
            exampleKo2 = "Korean Example 2-2"
        ),
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEn1 = "English Example 2-1",
            exampleEn2 = "English Example 2-2",
            exampleKo1 = "Korean Example 2-1",
            exampleKo2 = "Korean Example 2-2"
        ),
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEn1 = "English Example 2-1",
            exampleEn2 = "English Example 2-2",
            exampleKo1 = "Korean Example 2-1",
            exampleKo2 = "Korean Example 2-2"
        ),
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEn1 = "English Example 2-1",
            exampleEn2 = "English Example 2-2",
            exampleKo1 = "Korean Example 2-1",
            exampleKo2 = "Korean Example 2-2"
        ),
        // Add more GrammarPoint objects if needed
    )
    val topik2 = listOf(
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 1",
            grammarDef1 = "Definition 1-1",
            grammarDef2 = "Definition 1-2",
            exampleEn1 = "English Example 1-1",
            exampleEn2 = "English Example 1-2",
            exampleKo1 = "Korean Example 1-1",
            exampleKo2 = "Korean Example 1-2"
        ),
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEn1 = "English Example 2-1",
            exampleEn2 = "English Example 2-2",
            exampleKo1 = "Korean Example 2-1",
            exampleKo2 = "Korean Example 2-2"
        ),
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEn1 = "English Example 2-1",
            exampleEn2 = "English Example 2-2",
            exampleKo1 = "Korean Example 2-1",
            exampleKo2 = "Korean Example 2-2"
        ),
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEn1 = "English Example 2-1",
            exampleEn2 = "English Example 2-2",
            exampleKo1 = "Korean Example 2-1",
            exampleKo2 = "Korean Example 2-2"
        ),
        GrammarPoint(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEn1 = "English Example 2-1",
            exampleEn2 = "English Example 2-2",
            exampleKo1 = "Korean Example 2-1",
            exampleKo2 = "Korean Example 2-2"
        ),
        // Add more GrammarPoint objects if needed
    )

    val grammarLevel1 = GrammarLevel(
        title = "Level 1",
        isUnlocked = true,
        grammarList = topik1
    )
    val grammarLevel2 = GrammarLevel(
        title = "Level 2",
        isUnlocked = false,
        grammarList = topik2
    )

    var expanded by remember {
        mutableStateOf(false)
    }
    var expanded2 by remember {
        mutableStateOf(false)
    }

    MalMalITheme(false) {
        LazyColumn {

            item {
                GrammarContainer(
                    grammarLevel = grammarLevel1,
                    isEditingMode = false,
                    isExpanded = expanded,
                    onSelectClick = {

                    },
                    onExpandClick = {
                        expanded = !expanded
                    }

                )
            }
            item {

                Spacer(Modifier.height(24.dp))
            }
            item {

                GrammarContainer(
                    grammarLevel = grammarLevel2,
                    isEditingMode = false,
                    isExpanded = expanded2,
                    onSelectClick = {

                    },
                    onExpandClick = {
                        expanded2 = !expanded2
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun Preview_GrammarListContainer() {

    MalMalITheme(false) {
        GrammarListContainer(
            isEditingMode = true,
            levels = exampleGrammarLevelList
        )
    }
}

@Preview
@Composable
fun Preview_OnboardingContainer() {
    MalMalITheme(false) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OnboardingContainer(
                modifier = Modifier.weight(1f),
                icon = MR.images.cat_icon,
                title = "Welcome to MalMalI",
                text = "MalMalI is here to help you level up your Korean skills. Whether it is vocab memorization, grammar practice, or chatting, MalMalI is here to help."
            )

        }
    }
}

@Preview
@Composable
fun Preview_PagerIndicator() {
    MalMalITheme(false) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var state by remember {
                mutableIntStateOf(1)
            }
            PagerIndicator(
                modifier = Modifier,
                currentPage = state,
                numberOfPages = 4
            )
            Spacer(Modifier.height(36.dp))
            SkipAndNextButton(
                onNext = {
                    if (state < 4) {
                        state++
                    } else {
                        state = 1
                    }
                },
                onSkip = {

                }
            )

        }
    }
}

@Preview
@Composable
fun Preview_ChatTextField() {
    var text by remember {
        mutableStateOf("")
    }
    MalMalITheme(false) {
        Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            Spacer(Modifier.weight(1f))
            ChatTextField(
                text = text,
                onValueChange = {
                    text = it
                },
                micMode = false,
                recording = false,
                onFinish = {

                }
            )
            Spacer(Modifier.weight(1f))

        }
    }
}

@Preview
@Composable
fun Preview_ChatBubble() {
    var showOption by remember { mutableStateOf(false) }

    MalMalITheme(false) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ChatBubble(
                text = "안녕하세요, 저는 말말이입니다!",
                isUser = false,
                showOptions = false,
            ) {
                if (showOption) {
                    showOption = false
                }
            }
            ChatBubble(
                text = "안녕하세어, 저는 헌터입니다. 취미가 있어요?",
                isUser = true,
                showOptions = false,
            ) {
                if (showOption) {
                    showOption = false
                }
            }
            ChatBubble(
                text = "저는 인공지능으로서 개인적인 취미를 가질 수 없습니다. 하지만 사용자들의 다양한 질문에 답하고, 정보를 제공하는 것을 '취미'라고 본다면, 그것이 제 '취미'라고 할 수 있겠네요. 사용자가 궁금해하는 것이나 관심사에 대해 어떻게 도움을 줄 수 있는지 알려주세요!!",
                isUser = false,
                showOptions = false,
            ) {
                if (showOption) {
                    showOption = false
                }
            }
            ChatBubble(
                text = "뭐지...?",
                isUser = true,
                showOptions = false,
            ) {
                if (showOption) {
                    showOption = false
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview_ChatPopUpAlert() {
    var showOption by remember { mutableStateOf(false) }
    MalMalITheme(false) {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

            }
            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ChatPopUpAlert(
                    onFavoriteClick = {},
                    onShareClick = {},
                    onInformationClick = {},
                    onCopyClick = {}
                )
                ChatBubble(
                    text = "안녕하세요, 저는 말말이입니다!",
                    isUser = false,
                    onClick = {
                        if (showOption) {
                            showOption = false
                        }
                    },
                    showOptions = showOption
                )
                ChatBubble(
                    text = "안녕하세어, 저는 헌터입니다. 취미가 있어요?",
                    isUser = true,
                    showOptions = false,
                ) {
                    if (showOption) {
                        showOption = false
                    }
                }
                ChatBubble(
                    text = "저는 인공지능으로서 개인적인 취미를 가질 수 없습니다. 하지만 사용자들의 다양한 질문에 답하고, 정보를 제공하는 것을 '취미'라고 본다면, 그것이 제 '취미'라고 할 수 있겠네요. 사용자가 궁금해하는 것이나 관심사에 대해 어떻게 도움을 줄 수 있는지 알려주세요!!",
                    isUser = false,
                    showOptions = false,
                ) {
                    if (showOption) {
                        showOption = false
                    }
                }
                ChatBubble(
                    text = "뭐지...?",
                    isUser = true,
                    showOptions = false,
                ) {
                    if (showOption) {
                        showOption = false
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun Preview_ChatAwaitAnimation() {
    MalMalITheme(false) {
        ChatAwaitAnimation()
    }
}

/*
@Preview
@Composable
fun Preview_ConversationScreen() {
    val conversationList = listOf(
        ConversationUi(
            id = 1,
            role = "User",
            content = "Hi, I'm having trouble accessing my account.",
            selected = false
        ),
        ConversationUi(
            id = 2,
            role = "Agent",
            content = "Hello! I'd be happy to help you. Could you provide me with your account email, please?",
            selected = false
        ),
        ConversationUi(
            id = 3,
            role = "User",
            content = "Sure, it's user@example.com.",
            selected = false
        ),
        ConversationUi(
            id = 4,
            role = "Agent",
            content = "Thank you. One moment while I check your account details.",
            selected = false
        ),
        ConversationUi(
            id = 5,
            role = "Agent",
            content = "It looks like there's a temporary block on your account due to suspicious activity. I can help you resolve this.",
            selected = true
        ),
        ConversationUi(
            id = 6,
            role = "User",
            content = "Oh, I see. What do I need to do to remove the block?",
            selected = false
        ),
        ConversationUi(
            id = 7,
            role = "Agent",
            content = "You'll need to verify your identity with a photo ID. Can you upload a photo ID through our secure form?",
            selected = false
        ),
        ConversationUi(
            id = 8,
            role = "User",
            content = "Yes, I can do that. Where can I find the form?",
            selected = false
        ),
        ConversationUi(
            id = 9,
            role = "Agent",
            content = "I'm sending you the link to the form now. [Link to form]",
            selected = false
        ),
        ConversationUi(
            id = 10,
            role = "User",
            content = "I've submitted the form with my ID.",
            selected = false
        ),
        ConversationUi(
            id = 11,
            role = "Agent",
            content = "Great, your identity has been verified, and I've removed the block. You should be able to access your account now.",
            selected = true
        ),
        ConversationUi(
            id = 12,
            role = "User",
            content = "Thank you so much for your help!",
            selected = false
        ),
        ConversationUi(
            id = 13,
            role = "Agent",
            content = "You're welcome! If you have any more questions or need further assistance, feel free to contact us. Have a great day!",
            selected = false
        )
    )
    MalMalITheme(false) {
        ConversationScreen(conversationList, isLoading = true)
    }
}*/
