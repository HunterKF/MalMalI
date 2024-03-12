package com.jaegerapps.malmali.vocabulary.domain.mappers

import com.jaegerapps.malmali.common.models.IconResource
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetDTOWithoutData
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetModel
import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.common.models.VocabularyCardModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class VocabSetMapperTest {

    @Test
    fun `toVocabSetDTO correctly maps VocabSetModel to VocabSetDTO`() {
        // Arrange
        val vocabularySetModel = VocabularySetModel(
            localId = 123,
            remoteId = 456,
            title = "Test Set",
            icon = IconResource.Bear_One,
            isAuthor = true,
            isPublic = true,
            tags = listOf("tag1", "tag2"),
            dateCreated = "2022-01-01",
            cards = listOf(
                VocabularyCardModel(1, "Word1", "Definition1", false),
                VocabularyCardModel(2, "Word2", "Definition2", false)
            )
        )

        // Act
        val vocabSetDTO = vocabularySetModel.toVocabSetDTO()

        // Assert
        assertEquals(vocabularySetModel.remoteId, vocabSetDTO.id)
        vocabSetDTO.tags.forEachIndexed { index, word ->
            assertEquals(vocabularySetModel.tags.toTypedArray()[index], word)

        }
        assertEquals(vocabularySetModel.isPublic, vocabSetDTO.is_public)
        assertTrue(vocabSetDTO.subscribed_users.isEmpty())
        assertNull(vocabSetDTO.author_id)
        assertEquals(vocabularySetModel.dateCreated, vocabSetDTO.created_at)
        assertEquals(vocabularySetModel.title, vocabSetDTO.set_title)
        assertEquals(vocabularySetModel.icon.tag, vocabSetDTO.set_icon)
        assertEquals(
            vocabularySetModel.cards.map { it.word }.joinToString(),
            vocabSetDTO.vocabulary_word.joinToString()
        )
        for (i in 0 until vocabularySetModel.cards.size) {
            assertEquals(
                vocabularySetModel.cards.map { it.word }.toTypedArray()[i],
                vocabSetDTO.vocabulary_word[i]
            )
            assertEquals(
                vocabularySetModel.cards.map { it.definition }.toTypedArray()[i],
                vocabSetDTO.vocabulary_definition[i]
            )

        }
    }

    @Test
    fun `toVocabSetModel correctly maps VocabSetDTO to VocabSetModel`() {
        // Arrange
        val vocabSetDTO = VocabSetDTO(
            id = 456,
            tags = arrayOf("tag1", "tag2"),
            is_public = true,
            subscribed_users = arrayOf("user1", "user2"),
            author_id = "author123",
            created_at = "2022-01-01",
            set_title = "Test Title",
            set_icon = "iconTag",
            vocabulary_word = arrayOf("Word1", "Word2"),
            vocabulary_definition = arrayOf("Definition1", "Definition2")
        )
        val isAuthor = true

        // Act
        val vocabSetModel = vocabSetDTO.toVocabSetModel(isAuthor)

        // Assert
        assertNull(vocabSetModel.localId)
        assertEquals(vocabSetDTO.id, vocabSetModel.remoteId)
        assertEquals(vocabSetDTO.set_title, vocabSetModel.title)
        // Assuming IconResource.resourceFromTag implementation is correct
        assertNotNull(vocabSetModel.icon)
        assertEquals(isAuthor, vocabSetModel.isAuthor)
        assertEquals(vocabSetDTO.is_public, vocabSetModel.isPublic)
        assertEquals(vocabSetDTO.tags.toList(), vocabSetModel.tags)
        assertEquals(vocabSetDTO.created_at, vocabSetModel.dateCreated)
        assertEquals(vocabSetDTO.vocabulary_word.size, vocabSetModel.cards.size)
        vocabSetModel.cards.forEachIndexed { index, card ->
            assertEquals(vocabSetDTO.vocabulary_word[index], card.word)
            assertEquals(vocabSetDTO.vocabulary_definition[index], card.definition)
        }
    }

    @Test
    fun `toVocabSetDTOWithoutData correctly maps VocabSetModel to VocabSetDTOWithoutData`() {
        // Arrange
        val vocabularySetModel = VocabularySetModel(
            localId = 123,
            remoteId = 456,
            title = "Test Title",
            icon = IconResource.Bear_One, // Assuming IconResource is a class with a 'tag' property
            isAuthor = true,
            isPublic = true,
            tags = listOf("tag1", "tag2"),
            dateCreated = "2022-01-01",
            cards = listOf(
                VocabularyCardModel(1, "Word1", "Definition1"),
                VocabularyCardModel(2, "Word2", "Definition2")
            )
        )

        // Act
        val vocabSetDTOWithoutData = vocabularySetModel.toVocabSetDTOWithoutData()

        // Assert
        for (i in 0 until vocabularySetModel.tags.size) {
            assertEquals(vocabularySetModel.tags.toTypedArray()[i], vocabSetDTOWithoutData.tags[i])

        }
        assertEquals(vocabularySetModel.isPublic, vocabSetDTOWithoutData.is_public)
        assertTrue(vocabSetDTOWithoutData.subscribed_users.isEmpty())
        assertEquals(vocabularySetModel.title, vocabSetDTOWithoutData.set_title)
        assertEquals(vocabularySetModel.icon.tag, vocabSetDTOWithoutData.set_icon)
        for (i in 0 until vocabularySetModel.cards.size) {
            assertEquals(
                vocabularySetModel.cards.map { it.word }.toTypedArray()[i],
                vocabularySetModel.cards[i].word
            )
            assertEquals(
                vocabularySetModel.cards.map { it.definition }.toTypedArray()[i],
                vocabularySetModel.cards[i].definition

            )

        }
    }
}