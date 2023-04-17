package com.thomaskioko.tvmaniac.datastore.implemetation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import app.cash.turbine.test
import com.thomaskioko.tvmaniac.util.model.AppCoroutineScope
import com.thomaskioko.tvmaniac.datastore.api.Theme
import com.thomaskioko.tvmaniac.datastore.implementation.DatastoreRepositoryImpl
import com.thomaskioko.tvmaniac.datastore.implementation.DatastoreRepositoryImpl.Companion.KEY_THEME
import com.thomaskioko.tvmaniac.datastore.implementation.IgnoreIos
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toPath
import kotlin.test.AfterTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DatastoreRepositoryImplTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private var preferencesScope: CoroutineScope = CoroutineScope(testDispatcher + Job())
    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        scope = preferencesScope,
        produceFile = { "test.preferences_pb".toPath() },
    )

    private val coroutineScope = AppCoroutineScope(
        io = preferencesScope,
        default = preferencesScope,
        main = preferencesScope
    )

    private val repository = DatastoreRepositoryImpl(
        coroutineScope= coroutineScope,
        dataStore = dataStore
    )


    @AfterTest
    fun clearDataStore() = runTest {
        dataStore.edit {
            it.remove(KEY_THEME)
        }
        preferencesScope.cancel()
    }

    @IgnoreIos
    @Test
    fun default_theme_is_emitted() = runTest {
        repository.observeTheme().test {
            awaitItem() shouldBe Theme.SYSTEM
        }
    }

    @IgnoreIos
    @Test
    fun when_theme_is_changed_correct_value_is_set() = runTest {

        repository.observeTheme().test {

            repository.saveTheme(Theme.DARK)
            awaitItem() shouldBe Theme.SYSTEM //Default theme
            awaitItem() shouldBe Theme.DARK
        }
    }
}