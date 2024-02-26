import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import kotlinx.coroutines.flow.Flow

interface VocabularySetSourceFunctions {
    //set queries
    suspend fun addSet(
        title: String,
        size: Long,
        tags: String?,
        isPrivate: SetMode,
        id: Long?,
        dateCreated: Long
    )
    suspend fun getSet(title: String, date: Long): VocabSet
    fun getSetAsFlow(setTitle: String, date: Long): Flow<VocabSet>
    fun getAllSets(): Flow<List<VocabSet>>
    suspend fun deleteSet(setId: Long)
    suspend fun updateSet(set: VocabSet)

    //card queries
    suspend fun insertCards(list: List<UiFlashcard>, linkedSetId: Long)
    fun getAllSetCards(setId: Long): Flow<List<UiFlashcard>>
    suspend fun updateCard(card: UiFlashcard)
    suspend fun deleteSingleCard(card: UiFlashcard)
    suspend fun deleteAllCards(setId: Long)

}