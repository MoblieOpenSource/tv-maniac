package com.thomaskioko.tvmaniac.core.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration

actual fun inMemorySqlDriver(): SqlDriver = NativeSqliteDriver(
    DatabaseConfiguration(
        name = "tvmaniac.test.db",
        version = 1,
        inMemory = true,
        create = { connection ->
            wrapConnection(connection) { TvManiacDatabase.Schema.create(it) }
        },
        upgrade = { connection, oldVersion, newVersion ->
            wrapConnection(connection) { TvManiacDatabase.Schema.migrate(it, oldVersion, newVersion) }
        },
    ),
)
