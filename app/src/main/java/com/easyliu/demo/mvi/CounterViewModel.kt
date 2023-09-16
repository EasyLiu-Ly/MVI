package com.easyliu.demo.mvi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

/**
 * @author easyliu
 * @date 2023/8/9 23:00
 */

@Module
@InstallIn(SingletonComponent::class)
object CounterUiStateModule {

    @Provides
    fun provideCounterUiState(): CounterUiState {
        return CounterUiState()
    }
}

@HiltViewModel
class CounterViewModel @Inject constructor(initialState: CounterUiState) :
        BaseMviViewModel<CounterUiState, CounterUiIntent, CounterEvent>(initialState) {

    override fun handleIntent(uiIntent: CounterUiIntent) {
        when (uiIntent) {
            is CounterUiIntent.Add -> {
                updateState {
                    copy(count = count + 1)
                }
                //showToast
                sendEvent(CounterEvent.ShowAddToast)
            }

            is CounterUiIntent.Decrease -> {
                updateState {
                    copy(count = count - 1)
                }
                //showToast
                sendEvent(CounterEvent.ShowDecreaseToast)
            }
        }
    }
}