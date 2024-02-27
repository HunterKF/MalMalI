package core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = MalMalIDatabase.Schema,
            context = context,
            name = "MalMalIDatabase.db"
        )
    }
}