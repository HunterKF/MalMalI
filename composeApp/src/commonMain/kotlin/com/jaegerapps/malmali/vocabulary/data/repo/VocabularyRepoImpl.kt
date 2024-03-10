package com.jaegerapps.malmali.vocabulary.data.repo

import com.jaegerapps.malmali.vocabulary.data.local.VocabularyLocalDataSource
import com.jaegerapps.malmali.vocabulary.data.remote.VocabularyRemoteDataSource
import com.jaegerapps.malmali.vocabulary.domain.mapper.toFlashcardEntity
import com.jaegerapps.malmali.vocabulary.domain.mapper.toSetEntity
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSet
import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetDTOWithoutData
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.Knower
import core.Knower.d
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VocabularyRepoImpl(
    private val remote: VocabularyRemoteDataSource,
    private val local: VocabularyLocalDataSource,
) : VocabularyRepo {
    override suspend fun createSet(
        vocabSetModel: VocabSetModel,
    ): Resource<Boolean> {
        return try {
            val dto = vocabSetModel.toVocabSetDTOWithoutData()
            val result = remote.createSet(dto)
            if (result.data != null) {
                local.createSet(
                    result.data.toSetEntity(true),
                )
            }
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("addSet", "An error has occurred here: $e")
            Resource.Error(e)
        }
    }

    override suspend fun insertSetLocally(vocabSetModel: VocabSetModel): Resource<Boolean> {
        return try {
            val result = local.createSet(
                vocabSetModel.toSetEntity(null)
            )
            if (result.data == true) {
                Resource.Success(true)
            } else {
                Resource.Error(Throwable())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("addSet", "An error has occurred here: $e")
            Resource.Error(e)
        }
    }

    override suspend fun getLocalSet(setId: Int, remoteId: Int): Resource<VocabSetModel> {
        return try {
            val set = local.readSingleSet(setId.toLong())
            if (set.data != null) {
                val model = toVocabSetModel(set.data)
                Resource.Success(model)
            } else {
                Resource.Error(Throwable(message = "Unknown error."))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("getSet", "An error has occurred here: $e")
            Resource.Error(e)
        }
    }

    override fun getAllLocalSets(): Flow<List<VocabSetModel>> {
        //This function will be called in the folder screen. It will display all local sets, not the cards yet.
        return local.readAllSets().map { it.map { it.toVocabSet() } }
    }

    override suspend fun getAllRemotePublicSets(start: Long, end: Long): Resource<List<VocabSetModel>> {
        return try {
            val result = remote.readAllSets(start, end)
            if (result.data != null) {
                Resource.Success(result.data.map { it.toVocabSetModel(false) })
            } else {
                Resource.Error(Throwable())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("getAllRemotePublicSets", "An error has occurred here: $e")
            Resource.Error(e)
        }
    }

    override suspend fun searchPublicSets(title: String, start: Long, end: Long): Resource<List<VocabSetModel>> {
        return try {
            val result = remote.readBySearch(title, start, end)
            if (result.data != null) {
                Resource.Success(result.data.map { it.toVocabSetModel(false) })
            } else {
                Resource.Error(Throwable())

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("searchPublicSets", "An error has occurred here: $e")
            Resource.Error(e)
        }
    }


    override suspend fun deleteSet(setId: Int, remoteId: Int, isAuthor: Boolean): Resource<Boolean> {
        return try {
            if (isAuthor) {
                if (remote.deleteSet(remoteId).data == true) {
                    if (local.deleteSet(setId.toLong()).data == true) {
                        Resource.Success(true)
                    }
                }
            } else {
                if (local.deleteSet(setId.toLong()).data == true) {
                    Resource.Success(true)
                }
            }
            Resource.Error(Throwable())
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("getSet", "An error has occurred here: $e")
            Resource.Error(e)
        }
    }

    override suspend fun updateSet(set: VocabSetModel): Resource<Boolean> {
        return try {
            Knower.d("updateSet", "Here is the set: $set")
            if (set.isAuthor) {
                if (remote.updateSet(set.toVocabSetDTO()).data != null) {
                    if (local.updateSet(
                            setEntity = set.toSetEntity(set.localId!!.toLong()),
                            cardEntityList = set.cards.map { it.toFlashcardEntity(set.remoteId!!.toLong()) }).data == true
                    ) {
                        Resource.Success(true)
                    }
                }
            } else {
                if (local.updateSet(
                        setEntity = set.toSetEntity(set.localId!!.toLong()),
                        cardEntityList = set.cards.map { it.toFlashcardEntity(set.remoteId!!.toLong()) }).data == true
                ) {
                    Resource.Success(true)
                }
            }
            Resource.Error(Throwable())
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("getSet", "An error has occurred here: $e")
            Resource.Error(e)
        }
    }


}
