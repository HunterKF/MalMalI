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
import com.jaegerapps.malmali.chat.presentation.conversation.components.ChatAwaitAnimation
import com.jaegerapps.malmali.chat.presentation.conversation.components.ChatBubble
import com.jaegerapps.malmali.chat.presentation.conversation.components.ChatPopUpAlert
import com.jaegerapps.malmali.chat.presentation.conversation.components.ChatTextField
import com.jaegerapps.malmali.common.components.ActionButton
import com.jaegerapps.malmali.common.components.TopBarLogo
import com.jaegerapps.malmali.common.components.CustomTextFieldWithBlackBorder
import com.jaegerapps.malmali.common.components.IconContainer
import com.jaegerapps.malmali.common.components.SettingsAndModal
import com.jaegerapps.malmali.common.components.CustomNavigationDrawer
import com.jaegerapps.malmali.common.models.IconResource
import com.jaegerapps.malmali.grammar.presentation.components.GrammarContainer
import com.jaegerapps.malmali.grammar.presentation.components.GrammarListContainer
import com.jaegerapps.malmali.grammar.presentation.components.GrammarPointContainer
import com.jaegerapps.malmali.common.models.GrammarLevelModel
import com.jaegerapps.malmali.common.models.GrammarPointModel
import com.jaegerapps.malmali.grammar.presentation.components.LevelHeader
import com.jaegerapps.malmali.home.components.CardButton
import com.jaegerapps.malmali.home.components.LevelBar
import com.jaegerapps.malmali.home.components.UserIcon
import com.jaegerapps.malmali.onboarding.intro.components.OnboardingContainer
import com.jaegerapps.malmali.onboarding.intro.components.PagerIndicator
import com.jaegerapps.malmali.onboarding.intro.components.SkipAndNextButton
import com.jaegerapps.malmali.practice.practicescreen.presentation.components.PracticeContainer
import com.jaegerapps.malmali.practice.practicescreen.presentation.components.PracticeTextField
import com.jaegerapps.malmali.vocabulary.presentation.components.AddCardButton
import com.jaegerapps.malmali.vocabulary.presentation.components.EditVocabContainer
import com.jaegerapps.malmali.vocabulary.presentation.components.FolderContainer
import com.jaegerapps.malmali.vocabulary.presentation.components.SelectIcon
import com.jaegerapps.malmali.vocabulary.presentation.components.SelectableButton
import com.jaegerapps.malmali.vocabulary.presentation.components.TitleBox
import com.jaegerapps.malmali.common.models.VocabularyCardModel
import com.jaegerapps.malmali.vocabulary.presentation.study_set.components.VocabularyButtons
import com.jaegerapps.malmali.vocabulary.presentation.study_set.components.VocabularyContainer
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
            card = VocabularyCardModel(word = "먹다", definition = "to eat"),
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
        val isEditing by remember { mutableStateOf(true) }
        var level1Selected by remember { mutableStateOf(false) }
        var level2Selected by remember { mutableStateOf(false) }
        var level3Selected by remember { mutableStateOf(false) }
        var level4Selected by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            LevelHeader(
                title = "Topik 1",
                isSelected = level1Selected,
                isUnlocked = true,
                isEditing = isEditing,
                onExpandClick = {

                },
                onSelectClick = {
                    level1Selected = !level1Selected
                }
            )
            LevelHeader(
                title = "Topik 2",
                isEditing = isEditing,
                isUnlocked = true,
                isSelected = level2Selected,
                onExpandClick = {

                },
                onSelectClick = {
                    level2Selected = !level2Selected
                }
            )
            LevelHeader(
                title = "Topik 3",
                isUnlocked = true,
                isEditing = isEditing,
                isSelected = level3Selected,
                onExpandClick = {

                },
                onSelectClick = {
                    level3Selected = !level3Selected
                }
            )
            LevelHeader(
                title = "Topik 4",
                isEditing = isEditing,
                isUnlocked = true,
                isSelected = level4Selected,
                onExpandClick = {

                },
                onSelectClick = {
                    level4Selected = !level4Selected
                }
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
                    isSelected = false,
                    isExpanded = expanded,
                    onExpandClick = {
                        expanded = !expanded
                    },
                )
                Spacer(Modifier.height(18.dp))
                GrammarPointContainer(
                    title = "Sample Title",
                    definition1 = "Definition 1",
                    exampleKo1 = "Korean Example 1",
                    exampleEn1 = "English Example 1",
                    exampleKo2 = "Korean Example 2",
                    exampleEn2 = "English Example 2",
                    isSelected = false,
                    isExpanded = true,
                    onExpandClick = {},
                )
                Spacer(Modifier.height(18.dp))

                GrammarPointContainer(
                    title = "Sample Title",
                    definition1 = "Definition 1",
                    exampleKo1 = "Korean Example 1",
                    exampleEn1 = "English Example 1",
                    exampleKo2 = "Korean Example 2",
                    exampleEn2 = "English Example 2",
                    isSelected = false,
                    isExpanded = true,
                    onExpandClick = {},
                )
                Spacer(Modifier.height(18.dp))

                GrammarPointContainer(
                    title = "Sample Title",
                    definition1 = "Definition 1",
                    exampleKo1 = "Korean Example 1",
                    exampleEn1 = "English Example 1",
                    exampleKo2 = "Korean Example 2",
                    exampleEn2 = "English Example 2",
                    isSelected = false,
                    isExpanded = true,
                    onExpandClick = {},
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
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 1",
            grammarDef1 = "Definition 1-1",
            grammarDef2 = "Definition 1-2",
            exampleEng1 = "English Example 1-1",
            exampleEng2 = "English Example 1-2",
            exampleKor1 = "Korean Example 1-1",
            exampleKor2 = "Korean Example 1-2"
        ),
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEng1 = "English Example 2-1",
            exampleEng2 = "English Example 2-2",
            exampleKor1 = "Korean Example 2-1",
            exampleKor2 = "Korean Example 2-2"
        ),
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEng1 = "English Example 2-1",
            exampleEng2 = "English Example 2-2",
            exampleKor1 = "Korean Example 2-1",
            exampleKor2 = "Korean Example 2-2"
        ),
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEng1 = "English Example 2-1",
            exampleEng2 = "English Example 2-2",
            exampleKor1 = "Korean Example 2-1",
            exampleKor2 = "Korean Example 2-2"
        ),
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEng1 = "English Example 2-1",
            exampleEng2 = "English Example 2-2",
            exampleKor1 = "Korean Example 2-1",
            exampleKor2 = "Korean Example 2-2"
        ),
        // Add more GrammarPoint objects if needed
    )
    val topik2 = listOf(
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 1",
            grammarDef1 = "Definition 1-1",
            grammarDef2 = "Definition 1-2",
            exampleEng1 = "English Example 1-1",
            exampleEng2 = "English Example 1-2",
            exampleKor1 = "Korean Example 1-1",
            exampleKor2 = "Korean Example 1-2"
        ),
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEng1 = "English Example 2-1",
            exampleEng2 = "English Example 2-2",
            exampleKor1 = "Korean Example 2-1",
            exampleKor2 = "Korean Example 2-2"
        ),
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEng1 = "English Example 2-1",
            exampleEng2 = "English Example 2-2",
            exampleKor1 = "Korean Example 2-1",
            exampleKor2 = "Korean Example 2-2"
        ),
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEng1 = "English Example 2-1",
            exampleEng2 = "English Example 2-2",
            exampleKor1 = "Korean Example 2-1",
            exampleKor2 = "Korean Example 2-2"
        ),
        GrammarPointModel(
            grammarCategory = 1,
            grammarTitle = "Title 2",
            grammarDef1 = "Definition 2-1",
            grammarDef2 = "Definition 2-2",
            exampleEng1 = "English Example 2-1",
            exampleEng2 = "English Example 2-2",
            exampleKor1 = "Korean Example 2-1",
            exampleKor2 = "Korean Example 2-2"
        ),
        // Add more GrammarPoint objects if needed
    )

    val grammarLevelModel1 = GrammarLevelModel(
        id = 1,
        title = "Level 1",
        isUnlocked = true,
        grammarList = topik1
    )
    val grammarLevelModel2 = GrammarLevelModel(
        id = 2,
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
                    grammarLevelModel = grammarLevelModel1,
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
                    grammarLevelModel = grammarLevelModel2,
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
        val topik1 = listOf(
            GrammarPointModel(
                grammarCategory = 1,
                grammarTitle = "Title 1",
                grammarDef1 = "Definition 1-1",
                grammarDef2 = "Definition 1-2",
                exampleEng1 = "English Example 1-1",
                exampleEng2 = "English Example 1-2",
                exampleKor1 = "Korean Example 1-1",
                exampleKor2 = "Korean Example 1-2"
            ),
            GrammarPointModel(
                grammarCategory = 1,
                grammarTitle = "Title 2",
                grammarDef1 = "Definition 2-1",
                grammarDef2 = "Definition 2-2",
                exampleEng1 = "English Example 2-1",
                exampleEng2 = "English Example 2-2",
                exampleKor1 = "Korean Example 2-1",
                exampleKor2 = "Korean Example 2-2"
            ),
            GrammarPointModel(
                grammarCategory = 1,
                grammarTitle = "Title 2",
                grammarDef1 = "Definition 2-1",
                grammarDef2 = "Definition 2-2",
                exampleEng1 = "English Example 2-1",
                exampleEng2 = "English Example 2-2",
                exampleKor1 = "Korean Example 2-1",
                exampleKor2 = "Korean Example 2-2"
            ),
            GrammarPointModel(
                grammarCategory = 1,
                grammarTitle = "Title 2",
                grammarDef1 = "Definition 2-1",
                grammarDef2 = "Definition 2-2",
                exampleEng1 = "English Example 2-1",
                exampleEng2 = "English Example 2-2",
                exampleKor1 = "Korean Example 2-1",
                exampleKor2 = "Korean Example 2-2"
            ),
            GrammarPointModel(
                grammarCategory = 1,
                grammarTitle = "Title 2",
                grammarDef1 = "Definition 2-1",
                grammarDef2 = "Definition 2-2",
                exampleEng1 = "English Example 2-1",
                exampleEng2 = "English Example 2-2",
                exampleKor1 = "Korean Example 2-1",
                exampleKor2 = "Korean Example 2-2"
            ),
            // Add more GrammarPoint objects if needed
        )
        GrammarListContainer(
            isEditingMode = true,
            levels = listOf(
                GrammarLevelModel(
                    id = 1,
                    title = "Level 1",
                    isSelected = true,
                    isUnlocked = true,
                    grammarList = topik1,
                )
            ),
            onSelect = {

            }
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

@Preview
@Composable
fun Preview_ConversationScreen() {
    MalMalITheme(false) {
//        ConversationScreen()
    }
}


@Preview
@Composable
fun Preview_PracticeContainer() {
    MalMalITheme(false) {
        var grammarExpand by remember { mutableStateOf(false) }
        var vocabExpand by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            PracticeContainer(
                vocab = VocabularyCardModel(word = "가다", definition = "to go, to move"),
                grammar = GrammarPointModel(
                    grammarCategory = 1,
                    grammarTitle = "Noun usage",
                    grammarDef1 = "Definition of noun usage",
                    exampleEng1 = "This is a book.",
                    exampleEng2 = "She is a teacher.",
                    exampleKor1 = "이것은 책입니다.",
                    exampleKor2 = "그녀는 선생님입니다.",
                    selected = false
                ),
                vocabExpanded = vocabExpand,
                grammarExpanded = grammarExpand,
                onClick = {
                    if (grammarExpand) {
                        grammarExpand = false
                        vocabExpand = true
                    } else if (vocabExpand) {
                        grammarExpand = true
                        vocabExpand = false
                    } else {
                        vocabExpand = true
                    }
                }
            )
            PracticeContainer(
                vocab = VocabularyCardModel(word = "가다", definition = "to go, to move"),
                grammar = GrammarPointModel(
                    grammarCategory = 2,
                    grammarTitle = "Verb conjugation",
                    grammarDef1 = "Basic verb conjugation",
                    grammarDef2 = "Past tense conjugation",
                    exampleEng1 = "I eat an apple.",
                    exampleEng2 = "I ate an apple.",
                    exampleKor1 = "나는 사과를 먹는다.",
                    exampleKor2 = "나는 사과를 먹었다.",
                    selected = false
                ),
                vocabExpanded = true,
                grammarExpanded = false,
                onClick = {}
            )
            PracticeContainer(
                vocab = VocabularyCardModel(word = "가다", definition = "to go, to move"),
                grammar = GrammarPointModel(
                    grammarCategory = 2,
                    grammarTitle = "Verb conjugation",
                    grammarDef1 = "Basic verb conjugation",
                    grammarDef2 = "Past tense conjugation",
                    exampleEng1 = "I eat an apple.",
                    exampleEng2 = "I ate an apple.",
                    exampleKor1 = "나는 사과를 먹는다.",
                    exampleKor2 = "나는 사과를 먹었다.",
                    selected = false
                ),
                vocabExpanded = false,
                grammarExpanded = true,
                onClick = {
                }
            )
        }
    }
}

@Preview
@Composable
fun Preview_PracticeTextField() {
    var text by remember { mutableStateOf("") }
    MalMalITheme(false) {
        PracticeTextField(
            value = text,
            onValueChange = {
                text = it
            }
        )
    }
}

@Preview
@Composable
fun Preview_PracticeScreen() {
    MalMalITheme(false) {
//        PracticeScreen()
    }
}
