package com.easyliu.demo.mvi

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

/**
 * @author easyliu
 * @date 2023/8/7 16:51
 */
abstract class BaseMviViewModel<S : MviUiState, I : MviUiIntent, E : MviEvent>(initialState: S) : ViewModel() {
    private val mutableStateFlow = MutableStateFlow(initialState)
    val uiStateFlow = mutableStateFlow.asStateFlow()
    private val uiIntentFlow = MutableSharedFlow<I>()
    private val eventChanel = Channel<E>()
    val eventFlow = eventChanel.receiveAsFlow()

    protected abstract fun handleIntent(uiIntent: I)

    val state
        get() = uiStateFlow.value

    init {
        viewModelScope.launch {
            uiIntentFlow.collect {
                handleIntent(it)
            }
        }
    }

    protected fun updateState(reducer: S.() -> S) {
        viewModelScope.launch {
            mutableStateFlow.update {
                it.reducer()
            }
        }
    }

    fun setIntent(uiIntent: I) {
        viewModelScope.launch {
            uiIntentFlow.emit(uiIntent)
        }
    }

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            eventChanel.send(event)
        }
    }

    fun <T> Flow<T>.resolveSubscription(subscriptionScope: CoroutineScope? = null, action: suspend (T) -> Unit): Job {
        return (subscriptionScope as? LifecycleCoroutineScope)?.launchWhenCreated {
            yield()
            collectLatest(action)
        } ?: run {
            val scope = subscriptionScope ?: viewModelScope
            scope.launch(start = CoroutineStart.UNDISPATCHED) {
                // Use yield to ensure flow collect coroutine is dispatched rather than invoked immediately.
                // This is necessary when Dispatchers.Main.immediate is used in scope.
                // Coroutine is launched with start = CoroutineStart.UNDISPATCHED to perform dispatch only once.
                yield()
                collectLatest(action)
            }
        }
    }
}