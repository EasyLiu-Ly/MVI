package com.easyliu.demo.mvi

/**
 * @author easyliu
 * @date 2023/8/9 23:00
 */
class CounterViewModel(initialState: CounterUiState) : BaseMviViewModel<CounterUiState, CounterUiIntent>(initialState) {
    override fun handleIntent(uiIntent: CounterUiIntent) {
        when (uiIntent) {
            is CounterUiIntent.Add -> {
                updateState {
                    copy(count = count + 1)
                }
            }

            is CounterUiIntent.Decrease -> {
                updateState {
                    copy(count = count - 1)
                }
            }
        }
    }
}