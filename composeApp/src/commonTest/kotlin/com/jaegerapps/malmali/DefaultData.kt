package com.jaegerapps.malmali

import com.jaegerapps.malmali.grammar.domain.GrammarLevel
import com.jaegerapps.malmali.grammar.domain.GrammarPoint
import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.domain.UiFlashcard
import com.jaegerapps.malmali.vocabulary.domain.VocabSet

val exampleVocabSetList = listOf<VocabSet>(
    VocabSet(
        "Set 1",
        MR.images.cat_icon,
        expanded = false,
        setId = 1,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 11
    ),
    VocabSet(
        "Set 2",
        MR.images.cat_icon,
        expanded = false,
        setId = 2,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 12
    ),
    VocabSet(
        "Set 3",
        MR.images.cat_icon,
        expanded = false,
        setId = 3,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 13
    ),
    VocabSet(
        "Set 3",
        MR.images.cat_icon,
        expanded = false,
        setId = 3,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 14
    ),
    VocabSet(
        "Set 4",
        MR.images.cat_icon,
        expanded = false,
        setId = 4,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 15
    ),
    VocabSet(
        "Set 5",
        MR.images.cat_icon,
        expanded = false,
        setId = 5,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 16
    ),
    VocabSet(
        "Set 6",
        MR.images.cat_icon,
        expanded = false,
        setId = 6,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 17
    ),
    VocabSet(
        "Set 7",
        MR.images.cat_icon,
        expanded = false,
        setId = 7,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 18
    ),
    VocabSet(
        "Set 8",
        MR.images.cat_icon,
        expanded = false,
        setId = 8,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 19
    ),
    VocabSet(
        "Set 9",
        MR.images.cat_icon,
        expanded = false,
        setId = 9,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 20
    ),
    VocabSet(
        "Set 10",
        MR.images.cat_icon,
        expanded = false,
        setId = 10,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 21
    ),
    VocabSet(
        "Set 11",
        MR.images.cat_icon,
        expanded = false,
        setId = 11,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 22
    ),
    VocabSet(
        "Set 12",
        MR.images.cat_icon,
        expanded = false,
        setId = 12,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 23
    ),
    VocabSet(
        "Set 13",
        MR.images.cat_icon,
        expanded = false,
        setId = 13,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 24
    ),
    VocabSet(
        "Set 14",
        MR.images.cat_icon,
        expanded = false,
        setId = 14,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 25
    ),
    VocabSet(
        "Set 15",
        MR.images.cat_icon,
        expanded = false,
        setId = 15,
        isPrivate = SetMode.PUBLIC,
        dateCreated = 26
    )
)

val exampleUiFlashcardList = listOf<UiFlashcard>(
    UiFlashcard(
        cardId = 1,
        word = "먹다",
        def = "to eat",
        level = 1,
        error = false
    ),
    UiFlashcard(
        cardId = 2,
        word = "가다",
        def = "to go",
        level = 1,
        error = false
    ),
    UiFlashcard(
        cardId = 3,
        word = "마시다",
        def = "to drink",
        level = 1,
        error = false
    ),
    UiFlashcard(
        cardId = 4,
        word = "재밌다",
        def = "to be fun",
        level = 1,
        error = false
    ),
    UiFlashcard(
        cardId = 5,
        word = "성공하다",
        def = "to succeed",
        level = 1,
        error = false
    )
)

val exampleUiFlashcardListWithUiId = (exampleUiFlashcardList.indices).map {
    exampleUiFlashcardList[it].copy(
        uiId = it.toLong()
    )
}

val exampleGrammarPoint = GrammarPoint(
    grammarTitle = "Title 2",
    grammarDef1 = "Definition 2-1",
    grammarDef2 = "Definition 2-2",
    exampleEn1 = "English Example 2-1",
    exampleEn2 = "English Example 2-2",
    exampleKo1 = "Korean Example 2-1",
    exampleKo2 = "Korean Example 2-2"
)

val exampleGrammarPointList = (1..10).map {
    GrammarPoint(
        grammarTitle = "Point $it",
        grammarDef1 = "Definition $it-1",
        grammarDef2 = "Definition $it-2",
        exampleEn1 = "English Example $it-1",
        exampleEn2 = "English Example $it-2",
        exampleKo1 = "Korean Example $it-1",
        exampleKo2 = "Korean Example $it-2"
    )
}

val exampleGrammarLevel = GrammarLevel(
    title = "Level 1",
    isUnlocked = false,
    exampleGrammarPointList
)
val exampleGrammarLevelList = listOf(
    GrammarLevel(
        title = "Level 1",
        isUnlocked = false,
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