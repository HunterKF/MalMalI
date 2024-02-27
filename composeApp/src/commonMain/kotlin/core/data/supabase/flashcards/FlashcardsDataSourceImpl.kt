package core.data.supabase.flashcards

import core.Knower
import core.Knower.e
import core.domain.supabase.flashcards.FlashcardsDataSource
import core.domain.supabase.flashcards.models.FlashcardDto
import core.domain.supabase.flashcards.models.FlashcardEntity
import core.domain.supabase.flashcards.models.SetDto
import core.domain.supabase.flashcards.models.SetEntity
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from

class FlashcardsDataSourceImpl(
    private val client: SupabaseClient,
) : FlashcardsDataSource {
    override suspend fun uploadFlashSets(set: SetDto): Resource<SetEntity> {
        return try {
            val result = client.from("flash_sets").insert(set) {
                select()
            }.decodeSingle<SetEntity>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("uploadFlashSets", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }

    override suspend fun uploadFlashcards(
        linkedId: String,
        list: List<FlashcardEntity>,
    ): Resource<Boolean> {
        return try {
            client.from("flashcards").insert(list) {
                select()
            }.decodeList<FlashcardEntity>()
            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("uploadFlashcards", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }

    override suspend fun getFlashSetsForUser(userId: String): Resource<List<SetEntity>> {
        return try {
            val result = client.from("flash_sets").select() {
                filter {
                    isIn("subscribed_users", listOf(userId))
                }
            }.decodeList<SetEntity>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("getFlashSetsForUser", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }

    override suspend fun getFlashcards(linkedId: String): Resource<List<FlashcardEntity>> {
        return try {
            val result = client.from("flashcards").select() {
                filter {
                    eq("linked_set", linkedId)
                }
            }.decodeList<FlashcardEntity>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("getFlashcards", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }

    }

    override suspend fun getPublicFlashSets(): Resource<List<SetEntity>> {
        return try {
            val result = client.from("flash_sets").select() {
                filter {
                    eq("is_public", true)
                }
            }.decodeList<SetEntity>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("getPublicFlashSets", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }

    override suspend fun updateSet(set: SetDto): Resource<SetEntity> {
        return try {
            val result = client.from("flash_sets").update(set) {
                filter {
                    eq("id", set.id)
                }
            }.decodeSingle<SetEntity>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("updateSet", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }

    override suspend fun updateFlashcard(flashcard: FlashcardDto): Resource<FlashcardEntity> {
        return try {
            val result = client.from("flashcards").update(flashcard) {
                filter {
                    eq("id", flashcard.id)
                }
            }.decodeSingle<FlashcardEntity>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("updateFlashcard", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }

    override suspend fun deleteSet(setEntity: SetEntity): Resource<Boolean> {
        return try {
            client.from("flash_sets").delete {
                filter {
                    eq("id", setEntity.id)
                }
            }
            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("deleteSet", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }

    override suspend fun deleteAllFlashcardsForSet(setEntity: SetDto): Resource<Boolean> {
        return try {
            client.from("flashcards").delete {
                filter {
                    eq("linked_set", setEntity.linked_set)
                }
            }
            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("deleteAllFlashcardsForSet", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }

    override suspend fun deleteFlashcard(flashcard: FlashcardDto): Resource<Boolean> {
        return try {
            client.from("flashcards").delete {
                filter {
                    eq("id", flashcard.id)
                }
            }
            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("deleteFlashcard", "An error has occurred. ${e.error}")
            Resource.Error(e)
        }
    }


}