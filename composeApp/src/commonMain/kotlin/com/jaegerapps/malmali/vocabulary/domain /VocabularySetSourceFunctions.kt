import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface VocabularySetSourceFunctions {
    //set queries
    suspend fun addSet(
        vocabSet: VocabSet,
        username: String
    )
    suspend fun getSet(setId: Int, setTitle: String): Resource<VocabSet>
    suspend fun getAllSets(): Resource<List<VocabSet>>
    suspend fun deleteSet(setId: Int)
    suspend fun updateSet(set: VocabSet)
}