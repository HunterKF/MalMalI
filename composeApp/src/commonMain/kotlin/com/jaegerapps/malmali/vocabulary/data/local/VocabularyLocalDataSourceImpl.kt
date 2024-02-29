package com.jaegerapps.malmali.vocabulary.data.local

import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.vocabulary.data.models.FlashcardEntity
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.mapper.toFlashcardEntityList
import com.jaegerapps.malmali.vocabulary.domain.mapper.toSetEntity
import core.Knower
import core.Knower.e
import core.util.Resource

class VocabularyLocalDataSourceImpl(
    database: MalMalIDatabase,
) : VocabularyLocalDataSource {
    private val queries = database.flashCardsQueries
    override suspend fun createSet(
        setEntity: SetEntity,
        cards: List<FlashcardEntity>,
    ): Resource<Boolean> {
        return try {
            queries.insertSet(
                set_id = setEntity.set_id,
                public_id = setEntity.linked_set,
                set_title = setEntity.set_title,
                tags = setEntity.tags,
                date_created = setEntity.date_created,
                is_public = setEntity.is_public,
                is_author = setEntity.is_author,
                set_icon = setEntity.set_icon

            )
            cards.forEach {
                queries.insertFlashCard(
                    null,
                    card_word = it.word,
                    card_definition = it.definition,
                    linked_set = it.linked_set
                )
            }

            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun readSingleSet(setId: Long): Resource<SetEntity> {
        return try {
            val set = queries.selectOneSet(
                setId
            ).executeAsOne().toSetEntity()

            Resource.Success(set)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun readSingleSetCards(setId: Long): Resource<List<FlashcardEntity>> {
        return try {
            val cards = queries.selectSetCards(
                setId
            ).executeAsList().toFlashcardEntityList()

            Resource.Success(cards)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun readAllSets(): Resource<List<SetEntity>> {
        return try {
            val cards = queries.selectAllSets().executeAsList().map { it.toSetEntity() }

            Resource.Success(cards)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun updateSet(setEntity: SetEntity): Resource<Boolean> {
        return try {
            queries.updateSet(
                id = setEntity.set_id!!,
                setTitle = setEntity.set_title,
                tags = setEntity.tags,
                isPublic = setEntity.is_public,
            )
            Resource.Success(true)
        } catch(e:Exception){
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun updateSetCards(cards: List<FlashcardEntity>): Resource<Boolean> {
        return try {
            cards.forEach {
                queries.updateCard(
                    word = it.word,
                    meaning = it.definition,
                    id = it.id!!
                )
            }
            Resource.Success(true)
        } catch(e:Exception){
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun deleteSet(setEntity: SetEntity): Resource<Boolean> {
        return try {
            setEntity.set_id?.let {
                queries.deleteSet(it)
                queries.deleteSetCards(it)
            }
            Resource.Success(setEntity.set_id != null)
        }  catch(e:Exception){
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

}