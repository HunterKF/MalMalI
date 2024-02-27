package core.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = MalMalIDatabase.Schema,
            name = "MalMalIDatabase.db"
        )
    }
}