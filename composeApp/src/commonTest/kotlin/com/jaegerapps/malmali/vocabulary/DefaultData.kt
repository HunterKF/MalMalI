package com.jaegerapps.malmali.vocabulary

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel

val exampleVocabSetModelLists = listOf(
    VocabSetModel(
        setId = 1,
        title = "Fruits",
        icon = IconResource.resourceFromTag("bear 1"),
        isPublic = true,
        tags = listOf("Food", "Nature"),
        dateCreated = "2024-02-28",
        cards = listOf(
            VocabularyCardModel(word = "Apple", definition = "A round fruit with red, yellow, or green skin."),
            VocabularyCardModel(word = "Banana", definition = "A long curved fruit with a yellow skin.")
        )
    ),
    VocabSetModel(
        setId = 2,
        title = "Vegetables",
        icon = IconResource.resourceFromTag("bear 1"),
        isPublic = false,
        tags = listOf("Food", "Healthy"),
        dateCreated = "2024-02-27",
        cards = listOf(
            VocabularyCardModel(word = "Carrot", definition = "A long pointed orange vegetable."),
            VocabularyCardModel(word = "Tomato", definition = "A red or yellowish fruit with a juicy pulp.")
        )
    ),
    VocabSetModel(
        setId = 3,
        title = "Tech Terms",
        icon = IconResource.resourceFromTag("bear 1"),
        isPublic = true,
        tags = listOf("Technology", "Modern"),
        dateCreated = "2024-02-26",
        cards = listOf(
            VocabularyCardModel(word = "Computer", definition = "An electronic device for storing and processing data."),
            VocabularyCardModel(word = "Internet", definition = "A global computer network providing a variety of information.")
        )
    )
)

val exampleUiFlashcardList = listOf<VocabularyCardModel>(
    VocabularyCardModel(
        word = "먹다",
        def = "to eat",
        error = false
    ),
    VocabularyCardModel(
        word = "가다",
        def = "to go",
        error = false
    ),
    VocabularyCardModel(
        word = "마시다",
        def = "to drink",
        error = false
    ),
    VocabularyCardModel(
        word = "재밌다",
        def = "to be fun",
        error = false
    ),
    VocabularyCardModel(
        word = "성공하다",
        def = "to succeed",
        error = false
    )
)
val exampleVocabularyCardModelLists = listOf<VocabularyCardModel>(
    VocabularyCardModel(
        word = "먹다",
        definition = "to eat",
    ),
    VocabularyCardModel(
        word = "가다",
        definition = "to go",
    ),
    VocabularyCardModel(
        word = "마시다",
        definition = "to drink",
    ),
    VocabularyCardModel(
        word = "재밌다",
        definition = "to be fun",
    ),
    VocabularyCardModel(
        word = "성공하다",
        definition = "to succeed",
    )
)

val exampleUiFlashcardListWithUiId = (exampleUiFlashcardList.indices).map {
    exampleUiFlashcardList[it].copy(
        uiId = it
    )
}

val exampleGrammarPoint = GrammarPoint(
    grammarTitle = "Title 2",
    grammarDef1 = "Definition 2-1",
    grammarDef2 = "Definition 2-2",
    exampleEng1 = "English Example 2-1",
    exampleEng2 = "English Example 2-2",
    exampleKor1 = "Korean Example 2-1",
    exampleKor2 = "Korean Example 2-2",
    grammarCategory = 1
)

val exampleGrammarPointList = (1..10).map {
    GrammarPoint(
        grammarTitle = "Point $it",
        grammarDef1 = "Definition $it-1",
        grammarDef2 = "Definition $it-2",
        exampleEng1 = "English Example $it-1",
        exampleEng2 = "English Example $it-2",
        exampleKor1 = "Korean Example $it-1",
        exampleKor2 = "Korean Example $it-2",
        grammarCategory = 1
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