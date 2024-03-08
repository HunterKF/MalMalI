package com.jaegerapps.malmali.vocabulary.domain.mappers

import com.jaegerapps.malmali.composeApp.database.FlashcardSets
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.mapper.toFlashcardEntity
import com.jaegerapps.malmali.vocabulary.domain.mapper.toSetEntity
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSet
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EntityMappersTest {

    @Test
    fun `toSetEntity correctly maps VocabSetDTO to SetEntity with author`() {
        // Arrange
        val dto = VocabSetDTO(
            id = 1,
            tags = arrayOf("tag1", "tag2"),
            is_public = true,
            subscribed_users = arrayOf("user1", "user2"),
            author_id = "author123",
            created_at = "2022-01-01",
            set_title = "Test Title",
            set_icon = "icon_path",
            vocabulary_word = arrayOf("word1", "word2"),
            vocabulary_definition = arrayOf("definition1", "definition2")
        )
        val isAuthor = true

        // Act
        val entity = dto.toSetEntity(isAuthor)

        // Assert
        assertNull(entity.set_id)
        assertEquals(1L, entity.linked_set)
        assertEquals("Test Title", entity.set_title)
        assertEquals("tag1, tag2", entity.tags)
        assertEquals("2022-01-01", entity.date_created)
        assertEquals(1L, entity.is_author)
        assertEquals(1L, entity.is_public)
        assertEquals("icon_path", entity.set_icon)
        assertEquals("word1|&|word2", entity.vocabulary_word)
        assertEquals("definition1|&|definition2", entity.vocabulary_definition)
    }

    @Test
    fun `toFlashcardEntity creates correct list of FlashcardEntity from VocabSetDTO`() {
        // Arrange
        val dto = VocabSetDTO(
            id = 1,
            tags = arrayOf("tag1", "tag2"),
            is_public = true,
            subscribed_users = arrayOf("user1", "user2"),
            author_id = "author123",
            created_at = "2022-01-01",
            set_title = "Test Title",
            set_icon = "icon_path",
            vocabulary_word = arrayOf("word1", "word2"),
            vocabulary_definition = arrayOf("definition1", "definition2")
        )

        // Act
        val flashcardEntities = dto.toFlashcardEntity()

        // Assert
        assertEquals(2, flashcardEntities.size)
        flashcardEntities.forEachIndexed { index, flashcardEntity ->
            assertNull(flashcardEntity.id)
            assertEquals(dto.vocabulary_word[index], flashcardEntity.word)
            assertEquals(dto.vocabulary_definition[index], flashcardEntity.definition)
            assertEquals(dto.id!!.toLong(), flashcardEntity.linked_set)
        }
    }

    @Test
    fun `toFlashcardEntity handles empty vocabulary arrays`() {
        // Arrange
        val dto = VocabSetDTO(
            id = 1,
            tags = arrayOf("tag1", "tag2"),
            is_public = true,
            subscribed_users = arrayOf("user1", "user2"),
            author_id = "author123",
            created_at = "2022-01-01",
            set_title = "Test Title",
            set_icon = "icon_path",
            vocabulary_word = emptyArray(),
            vocabulary_definition = emptyArray()
        )

        // Act
        val flashcardEntities = dto.toFlashcardEntity()

        // Assert
        assertTrue(flashcardEntities.isEmpty())
    }


    @Test
    fun `toVocabSetModel correctly maps SetEntity and FlashcardEntity list to VocabSetModel`() {
        // Arrange
        val setEntity = SetEntity(
            set_id = 123L,
            linked_set = 456L,
            set_title = "Test Set",
            tags = "tag1 tag2",
            date_created = "2022-01-01",
            is_author = 1L,
            is_public = 1L,
            set_icon = "icon123",
            vocabulary_word = "태국어|&|중국어",
            vocabulary_definition = "Thai|&|Chinese"
        )


        // Act
        val vocabSetModel = toVocabSetModel(setEntity)

        // Assert
        assertEquals(setEntity.set_id?.toInt(), vocabSetModel.localId)
        assertEquals(setEntity.linked_set.toInt(), vocabSetModel.remoteId)
        assertEquals(setEntity.set_title, vocabSetModel.title)
        // Assuming IconResource.resourceFromTag implementation is correct
        assertNotNull(vocabSetModel.icon)
        assertEquals(setEntity.is_author == 1L, vocabSetModel.isAuthor)
        assertEquals(setEntity.is_public == 1L, vocabSetModel.isPublic)
        assertEquals(setEntity.tags?.split(" "), vocabSetModel.tags)
        assertEquals(setEntity.date_created, vocabSetModel.dateCreated)

        assertEquals("태국어", vocabSetModel.cards[0].word)
        assertEquals("중국어", vocabSetModel.cards[1].word)
        assertEquals("Thai", vocabSetModel.cards[0].definition)
        assertEquals("Chinese", vocabSetModel.cards[1].definition)
        // Additional assertions can be made to check the correctness of each card in the model list
    }

    @Test
    fun `toVocabSetModel handles empty FlashcardEntity list`() {
        // Arrange
        val setEntity = SetEntity(
            set_id = 123L,
            linked_set = 456L,
            set_title = "Test Set",
            tags = "tag1 tag2",
            date_created = "2022-01-01",
            is_author = 1L,
            is_public = 1L,
            set_icon = "icon123",
            vocabulary_word = "",
            vocabulary_definition = ""
        )

        // Act
        val vocabSetModel = toVocabSetModel(setEntity)

        // Assert
        println(vocabSetModel)
        assertTrue(vocabSetModel.cards.isEmpty())
    }

    @Test
    fun `toSetEntity correctly maps FlashcardSets to SetEntity`() {
        // Arrange
        val flashcardSets = FlashcardSets(
            set_id = 1L,
            public_id = 2L,
            set_title = "Test Set",
            tags = "tag1, tag2",
            date_created = "2022-01-01",
            is_author = 1L,
            is_public = 1L,
            set_icon = "icon_path",
            vocabulary_word = "태국어|&|중국어",
            vocabulary_definition = "Thai|&|Chinese"
        )

        // Act
        val setEntity = flashcardSets.toSetEntity()

        // Assert
        assertEquals(flashcardSets.set_id, setEntity.set_id)
        assertEquals(flashcardSets.public_id, setEntity.linked_set)
        assertEquals(flashcardSets.set_title, setEntity.set_title)
        assertEquals(flashcardSets.tags, setEntity.tags)
        assertEquals(flashcardSets.date_created, setEntity.date_created)
        assertEquals(flashcardSets.is_author, setEntity.is_author)
        assertEquals(flashcardSets.is_public, setEntity.is_public)
        assertEquals(flashcardSets.set_icon, setEntity.set_icon)
    }


    @Test
    fun `toVocabSet correctly maps SetEntity to VocabSetModel`() {
        // Arrange
        val setEntity = SetEntity(
            set_id = 123L,
            linked_set = 456L,
            set_title = "Test Set",
            tags = "tag1 tag2",
            date_created = "2022-01-01",
            is_author = 1L,
            is_public = 1L,
            set_icon = "icon123",
            vocabulary_word = "태국어|&|중국어",
            vocabulary_definition = "Thai|&|Chinese"
        )

        // Act
        val vocabSetModel = setEntity.toVocabSet()

        // Assert
        assertEquals(setEntity.set_id?.toInt(), vocabSetModel.localId)
        assertEquals(setEntity.linked_set.toInt(), vocabSetModel.remoteId)
        assertEquals(setEntity.set_title, vocabSetModel.title)
        // Assuming IconResource.resourceFromTag implementation is correct
        assertNotNull(vocabSetModel.icon)
        assertEquals(setEntity.is_author == 1L, vocabSetModel.isAuthor)
        assertEquals(setEntity.is_public == 1L, vocabSetModel.isPublic)
        assertEquals(setEntity.tags?.split(" "), vocabSetModel.tags)
        assertEquals(setEntity.date_created, vocabSetModel.dateCreated)
    }

    @Test
    fun `toVocabSet handles null fields in SetEntity`() {
        // Arrange
        val setEntity = SetEntity(
            set_id = null,
            linked_set = 456L,
            set_title = "Test Set",
            tags = null,
            date_created = null,
            is_author = 0L,
            is_public = 0L,
            set_icon = "icon123",
            vocabulary_word = "태국어|&|중국어",
            vocabulary_definition = "Thai|&|Chinese"
        )

        // Act
        val vocabSetModel = setEntity.toVocabSet()

        // Assert
        assertNull(vocabSetModel.localId)
        assertEquals(setEntity.linked_set.toInt(), vocabSetModel.remoteId)
        assertEquals(setEntity.set_title, vocabSetModel.title)
        // Other assertions similar to above, handling null cases
        assertNotNull(vocabSetModel.icon)
        assertEquals(setEntity.is_author == 1L, vocabSetModel.isAuthor)
        assertEquals(setEntity.is_public == 1L, vocabSetModel.isPublic)
        assertTrue(vocabSetModel.tags.isEmpty())
        assertEquals(setEntity.date_created, vocabSetModel.dateCreated)
    }

    @Test
    fun `toFlashcardEntity correctly maps VocabularyCardModel to FlashcardEntity`() {
        // Arrange
        val vocabCardModel = VocabularyCardModel(
            word = "Test Word",
            definition = "Test Definition",
            dbId = 123L // Assuming dbId is the local database ID
        )
        val remoteId = 456L

        // Act
        val flashcardEntity = vocabCardModel.toFlashcardEntity(remoteId)

        // Assert
        assertNull(flashcardEntity.id) // ID should be null as per the method implementation
        assertEquals(vocabCardModel.word, flashcardEntity.word)
        assertEquals(vocabCardModel.definition, flashcardEntity.definition)
        assertEquals(remoteId, flashcardEntity.linked_set)
    }


}