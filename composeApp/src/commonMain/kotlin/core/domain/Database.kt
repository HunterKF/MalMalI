package core.domain

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jaegerapps.malmali.composeApp.database.FlashcardSets
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import core.data.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

//This should be an interface and
class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = MalMalIDatabase(databaseDriverFactory.createDriver())

    fun getAllPlayers(): Flow<List<FlashcardSets>> {
        val players = database.flashCardsQueries.selectAllSets().asFlow().mapToList(Dispatchers.IO)
        println("players")
        return players
    }


    fun insertPlayer() {
        database.flashCardsQueries.insertSet(
            null,
            set_title = "Title",
            set_size = 20,
            tags = null,
            date_created = 10,
            is_public = 2
        )
    }
}