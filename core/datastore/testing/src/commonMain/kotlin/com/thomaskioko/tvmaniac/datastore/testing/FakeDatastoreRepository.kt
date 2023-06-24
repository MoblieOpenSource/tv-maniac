package com.thomaskioko.tvmaniac.datastore.testing

import com.thomaskioko.tvmaniac.datastore.api.AuthState
import com.thomaskioko.tvmaniac.datastore.api.DatastoreRepository
import com.thomaskioko.tvmaniac.datastore.api.Theme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeDatastoreRepository : DatastoreRepository {

    private val themeFlow: Channel<Theme> = Channel(Channel.UNLIMITED)
    private val authStateFlow: Channel<AuthState> = Channel(Channel.UNLIMITED)

    suspend fun setTheme(theme: Theme) {
        themeFlow.send(theme)
    }

    suspend fun setAuthState(authState: AuthState) {
        authStateFlow.send(authState)
    }

    override fun saveTheme(theme: Theme) {
        // no -op
    }

    override fun observeTheme(): Flow<Theme> = themeFlow.receiveAsFlow()

    override fun clearAuthState() {
        // no -op
    }

    override fun observeAuthState(): Flow<AuthState> = authStateFlow.receiveAsFlow()

    override suspend fun saveAuthState(authState: AuthState) {
        // no -op
    }

    override suspend fun getAuthState(): AuthState = AuthState()
}

val authenticatedAuthState = AuthState(
    isAuthorized = true,
    accessToken = "wrwjqoi294930uknfasf",
    refreshToken = "wrwjqoi294930uknfasf",
)
