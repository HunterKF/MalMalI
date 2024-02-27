package core.domain.supabase.flashcards

import core.domain.supabase.flashcards.models.FlashcardDto
import core.domain.supabase.flashcards.models.FlashcardEntity
import core.domain.supabase.flashcards.models.SetDto
import core.domain.supabase.flashcards.models.SetEntity
import core.util.Resource

interface FlashcardsDataSource {

    //CREATE
    suspend fun uploadFlashSets(set: SetDto): Resource<SetEntity>
    suspend fun uploadFlashcards(linkedId: String, list: List<FlashcardEntity>): Resource<Boolean>
    //READ
    suspend fun getFlashSetsForUser(userId: String): Resource<List<SetEntity>>
    suspend fun getFlashcards(linkedId: String): Resource<List<FlashcardEntity>>
    suspend fun getPublicFlashSets(): Resource<List<SetEntity>>
    //UPDATE
    suspend fun updateSet(set: SetDto): Resource<SetEntity>
    suspend fun updateFlashcard(flashcard: FlashcardDto): Resource<FlashcardEntity>
    //DELETE
    suspend fun deleteSet(setEntity: SetEntity): Resource<Boolean>
    suspend fun deleteAllFlashcardsForSet(setEntity: SetDto): Resource<Boolean>
    suspend fun deleteFlashcard(flashcard: FlashcardDto): Resource<Boolean>
}