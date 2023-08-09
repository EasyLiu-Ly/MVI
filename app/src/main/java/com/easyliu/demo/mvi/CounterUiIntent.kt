package com.easyliu.demo.mvi

/**
 * @author easyliu
 * @date 2023/8/9 23:01
 */
sealed class CounterUiIntent : MviUiIntent {
    object Add : CounterUiIntent()
    object Decrease : CounterUiIntent()
}