package com.thomaskioko.tvmaniac.base.util

import android.app.Application
import android.content.pm.PackageManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.tatarka.inject.annotations.Inject

@Inject
class AndroidAppUtils(
    private val context : Application
) : AppUtils {

    override fun isYoutubePlayerInstalled(): Flow<Boolean> = flow {

        val playerAppInstalled = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .firstOrNull { it.packageName == "com.google.android.webview" } != null

        emit(playerAppInstalled)
    }
}