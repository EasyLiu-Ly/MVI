package com.easyliu.demo.mvi

/**
 * @author easyliu
 * @date 2023/9/16 14:11
 */
sealed class CounterEvent : MviEvent {
    object ShowAddToast : CounterEvent()
    object ShowDecreaseToast : CounterEvent()
}