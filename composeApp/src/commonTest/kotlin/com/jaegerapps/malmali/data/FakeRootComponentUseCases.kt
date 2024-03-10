package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.RootComponentUseCases
import com.jaegerapps.malmali.grammar.models.GrammarPoint
import com.jaegerapps.malmali.vocabulary.domain.models.FlashSetEntity
import core.util.Resource

class FakeRootComponentUseCases: RootComponentUseCases {
    private val grammarLevels = listOf(
        GrammarLevel(
            id = 1,
            title = "Basic Grammar",
            isSelected = false,
            isUnlocked = true,
            grammarList = listOf(
                GrammarPoint(
                    grammarCategory = 1,
                    grammarTitle = "Nouns",
                    grammarDef1 = "Definition of Nouns",
                    exampleEng1 = "The cat is sleeping.",
                    exampleEng2 = "John reads a book.",
                    exampleKor1 = "고양이가 자고 있다.",
                    exampleKor2 = "존이 책을 읽고 있다.",
                    selected = false
                ),
                // Add more GrammarPoints as needed
            )
        ),
        GrammarLevel(
            id = 2,
            title = "Intermediate Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                GrammarPoint(
                    grammarCategory = 2,
                    grammarTitle = "Verbs",
                    grammarDef1 = "Definition of Verbs",
                    exampleEng1 = "She runs fast.",
                    exampleEng2 = "They are singing.",
                    exampleKor1 = "그녀는 빨리 달린다.",
                    exampleKor2 = "그들은 노래하고 있다.",
                    selected = false
                ),
                // Add more GrammarPoints as needed
            )
        ),
        // Repeat for levels 3 to 6
        GrammarLevel(
            id = 3,
            title = "Advanced Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevel(
            id = 4,
            title = "Professional Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevel(
            id = 5,
            title = "Expert Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevel(
            id = 6,
            title = "Master Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        )
    )

    override suspend fun getGrammar(): Resource<List<GrammarLevel>> {
        return Resource.Success(grammarLevels)
    }

    override suspend fun getSets(name: String): Resource<List<FlashSetEntity>> {
        TODO("Not yet implemented")
    }
}