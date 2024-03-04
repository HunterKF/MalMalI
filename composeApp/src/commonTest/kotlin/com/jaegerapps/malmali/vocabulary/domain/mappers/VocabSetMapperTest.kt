package com.jaegerapps.malmali.vocabulary.domain.mappers

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetDTOWithoutData
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class VocabSetMapperTest {

    @Test
    fun `toVocabSetDTO correctly maps VocabSetModel to VocabSetDTO`() {
        // Arrange
        val vocabSetModel = VocabSetModel(
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
        val vocabSetDTO = vocabSetModel.toVocabSetDTO()

        // Assert
        assertEquals(vocabSetModel.remoteId, vocabSetDTO.id)
        vocabSetDTO.tags.forEachIndexed { index, word ->
            assertEquals(vocabSetModel.tags.toTypedArray()[index], word)

        }
        assertEquals(vocabSetModel.isPublic, vocabSetDTO.is_public)
        assertTrue(vocabSetDTO.subscribed_users.isEmpty())
        assertNull(vocabSetDTO.author_id)
        assertEquals(vocabSetModel.dateCreated, vocabSetDTO.created_at)
        assertEquals(vocabSetModel.title, vocabSetDTO.set_title)
        assertEquals(vocabSetModel.icon.tag, vocabSetDTO.set_icon)
        assertEquals(
            vocabSetModel.cards.map { it.word }.joinToString(),
            vocabSetDTO.vocabulary_word.joinToString()
        )
        for (i in 0 until vocabSetModel.cards.size) {
            assertEquals(
                vocabSetModel.cards.map { it.word }.toTypedArray()[i],
                vocabSetDTO.vocabulary_word[i]
            )
            assertEquals(
                vocabSetModel.cards.map { it.definition }.toTypedArray()[i],
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
        val vocabSetModel = VocabSetModel(
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
        val vocabSetDTOWithoutData = vocabSetModel.toVocabSetDTOWithoutData()

        // Assert
        for (i in 0 until vocabSetModel.tags.size) {
            assertEquals(vocabSetModel.tags.toTypedArray()[i], vocabSetDTOWithoutData.tags[i])

        }
        assertEquals(vocabSetModel.isPublic, vocabSetDTOWithoutData.is_public)
        assertTrue(vocabSetDTOWithoutData.subscribed_users.isEmpty())
        assertEquals(vocabSetModel.title, vocabSetDTOWithoutData.set_title)
        assertEquals(vocabSetModel.icon.tag, vocabSetDTOWithoutData.set_icon)
        for (i in 0 until vocabSetModel.cards.size) {
            assertEquals(
                vocabSetModel.cards.map { it.word }.toTypedArray()[i],
                vocabSetModel.cards[i].word
            )
            assertEquals(
                vocabSetModel.cards.map { it.definition }.toTypedArray()[i],
                vocabSetModel.cards[i].definition

            )

        }
    }
}