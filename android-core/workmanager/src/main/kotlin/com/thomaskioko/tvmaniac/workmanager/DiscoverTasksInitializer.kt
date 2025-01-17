package com.thomaskioko.tvmaniac.workmanager

import me.tatarka.inject.annotations.Inject

@Inject
class DiscoverTasksInitializer(
    private val showTasks: Lazy<ShowTasks>,
) : AppInitializer {
    override fun init() {
        showTasks.value.setupDiscoverDailySyncs()
    }
}
