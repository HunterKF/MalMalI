package com.jaegerapps.malmali.practice.domain

import com.jaegerapps.malmali.common.models.IconResource
import com.jaegerapps.malmali.common.models.VocabularyCardModel
import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.practice.practicescreen.data.models.HistoryEntity
import com.jaegerapps.malmali.practice.practicescreen.domain.mappers.toHistoryEntity
import com.jaegerapps.malmali.practice.practicescreen.domain.models.HistoryModel
import com.jaegerapps.malmali.practice.practicescreen.domain.repo.PracticeRepo
import core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakePracticeRepo : PracticeRepo {
    private var levelList = mutableListOf(1)
    private var setIdList = mutableListOf(1)
    private val vocabularySetList = listOf(
        VocabularySetModel(
            localId = 1,
            remoteId = 101,
            title = "Basic Spanish",
            icon = IconResource.Bear_Eleven,
            isAuthor = true,
            isPublic = true,
            tags = listOf("Spanish", "Beginner"),
            dateCreated = "2022-01-15",
            cards = listOf(
                VocabularyCardModel(
                    uiId = 1,
                    word = "안녕",
                    definition = "Hello (informal greeting)",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 2,
                    word = "감사합니다",
                    definition = "Thank you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 3,
                    word = "사랑해",
                    definition = "I love you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 4,
                    word = "책",
                    definition = "Book",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 5,
                    word = "커피",
                    definition = "Coffee",
                    error = false
                )
            )

        ),
        VocabularySetModel(
            localId = 2,
            remoteId = 102,
            title = "Advanced French",
            icon = IconResource.Bear_Eight,
            isAuthor = false,
            isPublic = false,
            tags = listOf("French", "Advanced"),
            dateCreated = "2022-02-20",
            cards = listOf(
                VocabularyCardModel(
                    uiId = 1,
                    word = "안녕",
                    definition = "Hello (informal greeting)",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 2,
                    word = "감사합니다",
                    definition = "Thank you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 3,
                    word = "사랑해",
                    definition = "I love you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 4,
                    word = "책",
                    definition = "Book",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 5,
                    word = "커피",
                    definition = "Coffee",
                    error = false
                )
            )
        ),
        VocabularySetModel(
            localId = 3,
            remoteId = 103,
            title = "Business English",
            icon = IconResource.Bear_Fourteen,
            isAuthor = true,
            isPublic = true,
            tags = listOf("English", "Business"),
            dateCreated = "2022-03-30",
            cards = listOf(
                VocabularyCardModel(
                    uiId = 1,
                    word = "안녕",
                    definition = "Hello (informal greeting)",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 2,
                    word = "감사합니다",
                    definition = "Thank you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 3,
                    word = "사랑해",
                    definition = "I love you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 4,
                    word = "책",
                    definition = "Book",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 5,
                    word = "커피",
                    definition = "Coffee",
                    error = false
                )
            )
        ),
        VocabularySetModel(
            localId = 4,
            remoteId = 104,
            title = "Intermediate German",
            icon = IconResource.Bear_Fourteen,
            isAuthor = false,
            isPublic = true,
            tags = listOf("German", "Intermediate"),
            dateCreated = "2022-04-25",
            cards = listOf(
                VocabularyCardModel(
                    uiId = 1,
                    word = "안녕",
                    definition = "Hello (informal greeting)",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 2,
                    word = "감사합니다",
                    definition = "Thank you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 3,
                    word = "사랑해",
                    definition = "I love you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 4,
                    word = "책",
                    definition = "Book",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 5,
                    word = "커피",
                    definition = "Coffee",
                    error = false
                )
            )
        ),
        VocabularySetModel(
            localId = 5,
            remoteId = 105,
            title = "Italian for Travelers",
            icon = IconResource.Bear_Eleven,
            isAuthor = true,
            isPublic = false,
            tags = listOf("Italian", "Travel"),
            dateCreated = "2022-05-10",
            cards = listOf(
                VocabularyCardModel(
                    uiId = 1,
                    word = "안녕",
                    definition = "Hello (informal greeting)",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 2,
                    word = "감사합니다",
                    definition = "Thank you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 3,
                    word = "사랑해",
                    definition = "I love you",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 4,
                    word = "책",
                    definition = "Book",
                    error = false
                ),
                VocabularyCardModel(
                    uiId = 5,
                    word = "커피",
                    definition = "Coffee",
                    error = false
                )
            )
        )
    )
    private val set = VocabularySetModel(
        localId = 1,
        remoteId = 1,
        title = "Korean 101",
        icon = IconResource.Bear_Eleven,
        isAuthor = false,
        isPublic = true,
        tags = emptyList(),
        dateCreated = null
    )

    private var historyList = MutableStateFlow(listOf<HistoryEntity>())

    override suspend fun readSelectedLevels(): Resource<List<Int>> {
        return Resource.Success(levelList)
    }

    override suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>> {
        levelList = levels.toMutableList()
        return Resource.Success(levelList)

    }

    override suspend fun readSelectedSetIds(): Resource<List<Int>> {
        return Resource.Success(listOf(1))
    }

    override suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>> {
        setIdList = list.toMutableList()
        return Resource.Success(setIdList)
    }

    override suspend fun readVocabSets(): Resource<List<VocabularySetModel>> {
        return Resource.Success(vocabularySetList)
    }

    override suspend fun insertHistorySql(history: HistoryModel): Resource<Boolean> {
        historyList.update {
            it.plus(history.toHistoryEntity())
        }
        return Resource.Success(true)
    }

    override suspend fun insertHistorySupabase(history: HistoryModel): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun getDefaultSet(): Resource<VocabularySetModel> {
        return Resource.Success(set)
    }

    override fun getHistorySql(): Flow<List<HistoryEntity>> {
        return historyList
    }
}