package com.thomaskioko.tvmaniac.shared.base.wrappers

import com.thomaskioko.tvmaniac.presentation.discover.DiscoverState
import com.thomaskioko.tvmaniac.presentation.discover.DiscoverStateMachine
import com.thomaskioko.tvmaniac.presentation.discover.ShowsAction
import com.thomaskioko.tvmaniac.util.model.AppCoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

/**
 * A wrapper class around [DiscoverStateMachine] handling `Flow` and suspend functions on iOS.
 */
@Inject
class DiscoverStateMachineWrapper(
    private val scope: AppCoroutineScope,
    private val stateMachine: DiscoverStateMachine,
) {

    fun start(stateChangeListener: (DiscoverState) -> Unit) {
        scope.main.launch {
            stateMachine.state.collect {
                stateChangeListener(it)
            }
        }
    }

    fun dispatch(action: ShowsAction) {
        scope.main.launch {
            stateMachine.dispatch(action)
        }
    }

    fun cancel() {
        scope.main.cancel()
    }
}
